package com.example.pictureoftheday

import com.example.pictureoftheday.repository.api.PictureOfTheNasaRetrofitImpl
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthDate
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthListDateItem
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseListDate
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseDate
import com.example.pictureoftheday.viewmodel.PictureOfTheEarthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

const val KEY_SP = "key_sp"
const val KEY_CURRENT_THEME = "current_theme"
const val ThemeDefault = 0
const val ThemeOne = 1
const val ThemeSecond = 2
const val ThemeThree = 3

const val THEME1 = "Today"
const val THEME2 = "Yesterday"
const val THEME3 = "tdby"

fun getEarthListResponseDate(c:PictureOfTheEarthViewModel.CallbackListPicture) {

    var  pictureOfTheEarthDate:PictureOfTheEarthDate
    val pictureOfTheNasaRetrofitImpl: PictureOfTheNasaRetrofitImpl = PictureOfTheNasaRetrofitImpl()

    val calendar = Calendar.getInstance()
    val today = calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.time
    val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)


    val callback = object : Callback<PictureOfTheEarthResponseListDate> {
        override fun onResponse(
            call: Call<PictureOfTheEarthResponseListDate>,
            response: Response<PictureOfTheEarthResponseListDate>
        ) {

            if (response.isSuccessful) {
                if (response.body()?.isNotEmpty()==true){
                    c.getListPicture(PictureOfTheEarthDate(
                        response.body()!![0].image,
                        response.body()!![0].centroid_coordinates.lat,
                        response.body()!![0].centroid_coordinates.lon))
                }

            }
        }

        override fun onFailure(call: Call<PictureOfTheEarthResponseListDate>, t: Throwable) {
            c.getListPicture(PictureOfTheEarthDate(
                "",
                0.0,
                0.0))
        }
    }

    pictureOfTheNasaRetrofitImpl.getRetrofit()
        .getListPictureOfTheEarth(BuildConfig.NASA_API_KEY)
        .enqueue(callback)


}