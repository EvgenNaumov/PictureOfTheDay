package com.example.pictureoftheday.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.ActivityApiBottomBinding
import com.google.android.material.badge.BadgeDrawable

class ApiBottomActivity : AppCompatActivity() {
    lateinit var binding: ActivityApiBottomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_bottom_navigation_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, EarthFragment.newInstance()).commit()
                    true
                }
                R.id.action_bottom_navigation_system -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SystemFragment.newInstance()).commit()
                    false
                }
                R.id.action_bottom_navigation_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MarsFragment.newInstance()).commit()
                    true
                }
                else -> {
                    true
                }
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.action_bottom_navigation_mars

        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.action_bottom_navigation_mars)
        badge.number = 999999
        badge.maxCharacterCount = 6
        badge.badgeGravity = BadgeDrawable.TOP_START
        binding.bottomNavigation.removeBadge(R.id.action_bottom_navigation_mars)
    }
}