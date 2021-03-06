package com.example.pictureoftheday.repository.api

import com.example.pictureoftheday.repository.day.PictureOfTheDayResponseDate
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseDate
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseListDate
import com.example.pictureoftheday.repository.mars.PictureOfTheMarsResponseDate
import com.example.pictureoftheday.repository.systemsolar.PictureOfTheSystemResponseDate
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheNasaAPI {
    //day astronomic
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey:String): Call<PictureOfTheDayResponseDate>
    @GET("planetary/apod")
    fun getPictureOfTheYesterday(@Query("api_key") apiKey:String, @Query("date") dateKey:String): Call<PictureOfTheDayResponseDate>

    //earth
    @GET("planetary/earth/assets")
    fun getPictureOfTheEarth(@Query("api_key") apiKey:String, @Query("date") dateKey:String, @Query("lat") lat:Double, @Query("lon") lon:Double, @Query("dim") dim:Double): Call<PictureOfTheEarthResponseDate>

    //earth
    @GET("EPIC/api/natural")
    fun getListPictureOfTheEarth(@Query("api_key") apiKey:String): Call<PictureOfTheEarthResponseListDate>

    //mars
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPictureOfTheMars(@Query("api_key") apiKey:String, @Query("sol") sol:Int, @Query("page") page:Int, @Query("camera") camera:String): Call<PictureOfTheMarsResponseDate>

    //system
    @GET("planetary/apod")
    fun getPictureOfTheSystem(@Query("api_key") apiKey:String): Call<PictureOfTheSystemResponseDate>

    @GET("DONKI/FLR")
    fun getSolarFlare(
        @Query("api_key") apiKey: String,
        @Query("startDate") startDate: String,
    ): Call<List<PictureOfTheSystemResponseDate>>

/*

    @GET("planetary/apod")
    fun getPictureOfThePeriod(@Query("api_key") apiKey:String, @Query("start_date") startDateKey:String, @Query("end_date") endDateKey:String): Call<PictureOfTheDayResponseDate>
*/
}