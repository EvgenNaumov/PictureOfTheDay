package com.example.pictureoftheday.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.databinding.FragmentPictureOfTheMarsBinding
import com.example.pictureoftheday.databinding.FragmentPictureOfTheSystemBinding

class PictureOfTheDaySystem: Fragment() {
    private var _binding: FragmentPictureOfTheSystemBinding? = null
    private val binding: FragmentPictureOfTheSystemBinding
        get() = _binding!!


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

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDaySystem()
    }
}