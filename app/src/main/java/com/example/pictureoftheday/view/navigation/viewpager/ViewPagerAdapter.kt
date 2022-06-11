package com.example.pictureoftheday.view.navigation.viewpager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pictureoftheday.view.PictureOfTheDayEarth
import com.example.pictureoftheday.view.PictureOfTheDayMars
import com.example.pictureoftheday.view.PictureOfTheDaySystem
import com.example.pictureoftheday.view.navigation.EarthFragment
import com.example.pictureoftheday.view.navigation.MarsFragment
import com.example.pictureoftheday.view.navigation.SystemFragment

private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val SYSTEM_FRAGMENT = 2

class ViewPagerAdapter(private val fm:FragmentManager):FragmentStatePagerAdapter(fm) {

    private val fragments = arrayOf(PictureOfTheDayEarth(), PictureOfTheDayMars(), PictureOfTheDaySystem())//TODO HW зафиксировать фрагменты на своих местах

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            EARTH_FRAGMENT-> fragments[EARTH_FRAGMENT]
            MARS_FRAGMENT -> fragments[MARS_FRAGMENT]
            SYSTEM_FRAGMENT -> fragments[SYSTEM_FRAGMENT]
            else -> fragments[EARTH_FRAGMENT]
        }
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            EARTH_FRAGMENT-> "Earth"
            MARS_FRAGMENT-> "Mars"
            else -> "System"
        }
    }

}