package com.example.pictureoftheday.view.navigation.viewpager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.pictureoftheday.view.PictureOfTheEarthFragment
import com.example.pictureoftheday.view.PictureOfTheMarsFragment
import com.example.pictureoftheday.view.PictureOfTheSystemFragment

private const val TODAY_FRAGMENT = 0
private const val YESTERDAY_FRAGMENT = 1
private const val DBYESTERDAY_FRAGMENT = 2

class ViewPagerAdapter(private val fm:FragmentManager):FragmentStatePagerAdapter(fm) {

    private val fragments = arrayOf(PictureOfTheEarthFragment(), PictureOfTheMarsFragment(), PictureOfTheSystemFragment())//TODO HW зафиксировать фрагменты на своих местах

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            TODAY_FRAGMENT-> fragments[TODAY_FRAGMENT]
            YESTERDAY_FRAGMENT -> fragments[YESTERDAY_FRAGMENT]
            DBYESTERDAY_FRAGMENT -> fragments[DBYESTERDAY_FRAGMENT]
            else -> fragments[TODAY_FRAGMENT]
        }
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            TODAY_FRAGMENT -> "Today"
            YESTERDAY_FRAGMENT-> "Yesterday"
            DBYESTERDAY_FRAGMENT-> "DBYesterday"
            else -> "Today"
        }
    }

}