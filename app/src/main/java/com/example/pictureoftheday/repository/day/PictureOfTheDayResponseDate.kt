package com.example.pictureoftheday.repository.day

import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseDate(
    val date: String,
    val explanation: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String,
    @SerializedName("hdurl")
    val hdurl: String?
)