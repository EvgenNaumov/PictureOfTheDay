package com.example.pictureoftheday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPictureOfTheEarthBinding
import com.example.pictureoftheday.viewmodel.PictureOfTheEarthAppState
import com.example.pictureoftheday.viewmodel.PictureOfTheEarthViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class PictureOfTheEarthFragment:Fragment() {

    private var _binding: FragmentPictureOfTheEarthBinding? = null
    private val binding: FragmentPictureOfTheEarthBinding
        get() = _binding!!

    private val viewModel: PictureOfTheEarthViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheEarthViewModel::class.java)
    }

    private var callBackOnErrorLoad = object :  CallbackFragment{
        override fun onError(messageError: String) {
            context?.let {
                Snackbar.make(it, binding.mainviewEarth, messageError, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    interface CallbackFragment {
        fun onError(messageError: String)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPictureOfTheEarthBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest(callBackOnErrorLoad)

        initTabLayout()
    }

    private fun initTabLayout() {
        binding.tabLayoutEarth.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        viewModel.sendRequestToday(callBackOnErrorLoad)
                    }
                    1 -> {
                        viewModel.sendRequestYT(callBackOnErrorLoad)
                    }
                    2 -> {
                        viewModel.sendRequestTDBY(callBackOnErrorLoad)
                    }
                    else -> {viewModel.sendRequestToday(callBackOnErrorLoad)}
                }
                Toast.makeText(requireContext(), "${tab?.position}", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //TODO("Not yet implemented")
            }
        })
    }

    private fun renderData(pictureOfTheEarthAppState: PictureOfTheEarthAppState) {

        when (pictureOfTheEarthAppState) {
            is PictureOfTheEarthAppState.Error -> {

                binding.imageEarth.load(R.drawable.bg_earth)
            }
            is PictureOfTheEarthAppState.Loading -> {
                binding.imageEarth.load(R.drawable.bg_earth)
            }
            is PictureOfTheEarthAppState.Success -> {
                binding.imageEarth.load(pictureOfTheEarthAppState.pictureOfTheEarthResponseData.url){
                    placeholder(R.drawable.bg_earth)
                    error(R.drawable.ic_stat_no_connect)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheEarthFragment()
    }

}