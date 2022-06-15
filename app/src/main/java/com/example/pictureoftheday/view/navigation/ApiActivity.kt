package com.example.pictureoftheday.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.ActivityApiBinding
import com.example.pictureoftheday.view.navigation.viewpager.ViewPagerAdapter

class ApiActivity: AppCompatActivity() {

    lateinit var binding: ActivityApiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        binding.tabLayoutApi.setupWithViewPager(binding.viewPager)

        binding.tabLayoutApi.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        binding.tabLayoutApi.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        binding.tabLayoutApi.getTabAt(2)?.setIcon(R.drawable.ic_system)

        //binding.textOne.setTextColor(resources.getColor(R.color.red))
        // binding.textOne.setTextColor(resources.getColor(R.color.blu))
    }
}