package com.example.pictureoftheday

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pictureoftheday.view.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getRealStyle(getCurrentTheme()))
        setCurrenyTheme(getCurrentTheme())
        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,
                PictureOfTheDayFragment.newInstance()).commit()
        }
    }

    fun setCurrenyTheme(currentTheme:Int){
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme():Int{
        val sp = getSharedPreferences(KEY_SP, Context.MODE_PRIVATE)
        return sp.getInt(KEY_CURRENT_THEME,1)
    }

    private fun getRealStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeOne -> R.style.Theme_Blue
            ThemeSecond -> R.style.Theme_ripple_color
            ThemeThree -> R.style.Theme_green
            else -> R.style.Theme_PictureOfTheDay
        }
    }

}