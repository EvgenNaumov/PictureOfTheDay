package com.example.pictureoftheday.view.navigation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentApiBinding
import com.example.pictureoftheday.settings.SettingsFragment
import com.example.pictureoftheday.view.PictureOfTheEarthFragment
import com.example.pictureoftheday.view.PictureOfTheDayFragment
import com.example.pictureoftheday.view.PictureOfTheMarsFragment
import com.example.pictureoftheday.view.PictureOfTheSystemFragment

class FragmentApi : Fragment() {

    private var _binding: FragmentApiBinding? = null
    private val binding: FragmentApiBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationPicture.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_bottom_navigation_picture-> {
                    choiseFragment(PictureOfTheDayFragment.newInstance())
                    Log.d("@@@", "FragmentApi: action_bottom_navigation_picture")
                    true
                }
                R.id.action_bottom_navigation_earth -> {
                    choiseFragment(PictureOfTheEarthFragment.newInstance())
                    Log.d("@@@", "FragmentApi: action_bottom_navigation_earth")
                    true
                }
                R.id.action_bottom_navigation_db_mars -> {
                    choiseFragment(PictureOfTheMarsFragment.newInstance())
                    Log.d("@@@", "FragmentApi: action_bottom_navigation_db_mars")
                    true
                }
                R.id.action_bottom_navigation_system -> {
                    choiseFragment(PictureOfTheSystemFragment.newInstance())
                    Log.d("@@@", "FragmentApi: action_bottom_navigation_db_mars")
                    true
                }
                R.id.action_bottom_navigation_setup -> {
                    choiseFragment(SettingsFragment.newInstance())
                    Log.d("@@@", "FragmentApi: action_bottom_navigation_setup")
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.bottomNavigationPicture.selectedItemId = R.id.action_bottom_navigation_picture
        choiseFragment(PictureOfTheDayFragment.newInstance())

    }

    private fun choiseFragment(fm:Fragment){
        childFragmentManager.beginTransaction().replace(R.id.container_api, fm).apply {
            this.commit()
            this.addToBackStack("")
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentApi()
    }

}