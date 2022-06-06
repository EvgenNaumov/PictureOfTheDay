package com.example.pictureoftheday.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.example.pictureoftheday.R
import com.google.android.material.tabs.TabLayout

class MyTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.myTabLayout
) : TabLayout(context, attrs, defStyleAttr) {

    override fun onAnimationStart() {
        Log.d("@@@","onAnimationStart")
        super.onAnimationStart()
    }

    override fun callOnClick(): Boolean {
        Log.d("@@@","callOnClick")
        return super.callOnClick()
    }

    override fun performClick(): Boolean {
        Log.d("@@@","performClick")
        return super.performClick()
    }
}