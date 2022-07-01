package com.example.pictureoftheday.view.bahavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class ImageBehavior(contex: Context, attrs: AttributeSet?=null):CoordinatorLayout.Behavior<View>() {
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return (dependency is ImageView)
    }
}