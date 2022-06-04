package com.example.pictureoftheday.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheNasaRetrofitImpl {
    private val nasaBaseUrl = "https://api.nasa.gov/"
    fun getRetrofit():PictureOfTheNasaAPI{
        val pictureOfTheRetrofitAPI = Retrofit.Builder()
            .baseUrl(nasaBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return pictureOfTheRetrofitAPI.create(PictureOfTheNasaAPI::class.java)
    }
}