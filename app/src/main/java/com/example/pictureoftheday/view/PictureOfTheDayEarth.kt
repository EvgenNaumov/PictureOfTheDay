package com.example.pictureoftheday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.databinding.FragmentPictureOfTheEarthBinding
import com.example.pictureoftheday.databinding.FragmentPictureOfTheMarsBinding

class PictureOfTheDayEarth:Fragment() {

    private var _binding: FragmentPictureOfTheEarthBinding? = null
    private val binding: FragmentPictureOfTheEarthBinding
        get() = _binding!!


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

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayEarth()
    }

}