package com.example.pictureoftheday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.CircleCropTransformation
import com.bumptech.glide.Glide
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPictureOfTheMarsBinding
import com.example.pictureoftheday.viewmodel.PictureOfTheMarsAppState
import com.example.pictureoftheday.viewmodel.PictureOfTheMarsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlin.random.Random

class PictureOfTheMarsFragment : Fragment() {
    private var _binding: FragmentPictureOfTheMarsBinding? = null
    private val binding: FragmentPictureOfTheMarsBinding
        get() = _binding!!

    private val viewModel: PictureOfTheMarsViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheMarsViewModel::class.java)
    }
    private var callBackOnErrorLoad = object : CallbackOnError {
        override fun onError(messageError: String) {
            context?.let {
                Snackbar.make(it, binding.mainviewMars, messageError, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    interface CallbackOnError {
        fun onError(messageError: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureOfTheMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState==null){
            binding.imageMars.load(R.drawable.bg_mars)
        }

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest(callBackOnErrorLoad)

        initTabLayout()
    }

    private fun initTabLayout() {
        binding.tabLayoutMars.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                    else -> {
                        viewModel.sendRequestToday(callBackOnErrorLoad)
                    }
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

    private fun renderData(pictureOfTheMarsAppState: PictureOfTheMarsAppState) {

        when (pictureOfTheMarsAppState) {
            is PictureOfTheMarsAppState.Error -> {

                binding.imageMars.load(R.drawable.bg_mars)
            }
            is PictureOfTheMarsAppState.Loading -> {
                binding.imageMars.load(R.drawable.bg_mars)
            }
            is PictureOfTheMarsAppState.Success -> {
                if (pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos.isNotEmpty()) {
                    val randomphoto = Random(pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos.size-1).nextInt(pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos.size-1)
                    val url = pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos[randomphoto].imgSrc
                        //.replace("http","https",true)
                    binding.imageMars.load(url)
                    {
                        transformations(CircleCropTransformation())
                        placeholder(R.drawable.bg_mars)
                        error(R.drawable.ic_stat_no_connect)
                    }

//                    Glide.with(requireContext())
//                        .load(url)
//                        .into(binding.imageMars)

                binding.imgSrc.text = pictureOfTheMarsAppState.pictureOfTheMarsResponseData.photos[randomphoto].imgSrc.toString()


                } else {
                    binding.imageMars.load(R.drawable.bg_mars)
                    context?.let {
                        Snackbar.make(
                            requireView(),
                            "По вашему запросу данные отсутствуют",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
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
        fun newInstance() = PictureOfTheMarsFragment()
    }

}