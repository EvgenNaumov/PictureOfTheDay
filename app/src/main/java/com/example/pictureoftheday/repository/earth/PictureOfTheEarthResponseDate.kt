package com.example.pictureoftheday.repository.earth


import com.google.gson.annotations.SerializedName

data class PictureOfTheEarthResponseDate(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("resource")
    val resource: Resource,
    @SerializedName("service_version")
    val serviceVersion: String,
    @SerializedName("url")
    val url: String
)