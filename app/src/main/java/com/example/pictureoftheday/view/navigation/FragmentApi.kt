package com.example.pictureoftheday.view.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentApiBinding
import com.example.pictureoftheday.view.navigation.viewpager.ViewPagerAdapter

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
        binding.viewPager1.adapter = ViewPagerAdapter(childFragmentManager)

        binding.tabLayout1.setupWithViewPager(binding.viewPager1)
        binding.tabLayout1.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        binding.tabLayout1.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        binding.tabLayout1.getTabAt(2)?.setIcon(R.drawable.ic_system)

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