package com.example.pictureoftheday.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPictureOfTheSystemBinding
import com.example.pictureoftheday.viewmodel.PictureOfTheSystemAppState
import com.example.pictureoftheday.viewmodel.PictureOfTheSystemViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class PictureOfTheSystemFragment: Fragment(){

    private var _binding: FragmentPictureOfTheSystemBinding? = null
    private val binding: FragmentPictureOfTheSystemBinding
        get() = _binding!!

    private val viewModel: PictureOfTheSystemViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheSystemViewModel::class.java)
    }

    private var callBackOnErrorLoad = object :  CallbackFragment{
        override fun onError(messageError: String) {
            context?.let {
                Snackbar.make(it, binding.mainviewSystem, messageError, Snackbar.LENGTH_SHORT).show()
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
        _binding = FragmentPictureOfTheSystemBinding.inflate(inflater, container,false)
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
        binding.tabLayoutSystem.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

    private fun renderData(pictureOfTheSystemAppState: PictureOfTheSystemAppState) {

        when (pictureOfTheSystemAppState) {
            is PictureOfTheSystemAppState.Error -> {

                binding.textsystem.visibility = View.GONE
                binding.imageSystem.visibility = View.VISIBLE
                binding.imageSystem.load(R.drawable.ic_stat_no_connect)
                context?.let {
                    Snackbar.make(it, binding.mainviewSystem, pictureOfTheSystemAppState.error, Snackbar.LENGTH_SHORT).show()
                }
            }
            is PictureOfTheSystemAppState.Loading -> {
                binding.imageSystem.load(R.drawable.bg_system)
            }
            is PictureOfTheSystemAppState.Success -> {
                if (pictureOfTheSystemAppState.pictureOfTheSystemsResponseData.isNotEmpty()) {
                    binding.imageSystem.visibility = View.GONE
                    binding.textsystem.visibility = View.VISIBLE
                    binding.imageSystem.load(pictureOfTheSystemAppState.pictureOfTheSystemsResponseData[0].link){
                        placeholder(R.drawable.bg_system)
                        error(R.drawable.ic_stat_no_connect)
                    }
                    binding.textsystem.text =
                        pictureOfTheSystemAppState.pictureOfTheSystemsResponseData[0].link.toString()
                }
//                binding.imageSystem.load(pictureOfTheSystemAppState.pictureOfTheSystemsResponseData.url){
//
//                    placeholder(R.drawable.bg_earth)
//                    error(R.drawable.ic_stat_no_connect)
//                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheSystemFragment()
    }

}