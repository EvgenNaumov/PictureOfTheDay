package com.example.pictureoftheday.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheNasaAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey:String): Call<PictureOfTheDayResponseDate>
    @GET("planetary/apod")
    fun getPictureOfTheYesterday(@Query("api_key") apiKey:String, @Query("date") dateKey:String): Call<PictureOfTheDayResponseDate>
/*
    @GET("planetary/apod")
    fun getPictureOfThePeriod(@Query("api_key") apiKey:String, @Query("start_date") startDateKey:String, @Query("end_date") endDateKey:String): Call<PictureOfTheDayResponseDate>
*/
}