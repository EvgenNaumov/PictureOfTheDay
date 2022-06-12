package com.example.pictureoftheday.repository.api

import com.example.pictureoftheday.repository.day.PictureOfTheDayResponseDate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey:String): Call<PictureOfTheDayResponseDate>
}