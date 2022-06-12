package com.example.pictureoftheday.repository.systemsolar

import com.google.gson.annotations.SerializedName

data class PictureOfTheDaySystemResponseDate(
    var date:  String,
    var explanation: String,
    @SerializedName("media_type")
    var mediaType:  String,
    @SerializedName("service_version")
    var serviceVersion:  String,
    var title: String,
    var url:  String
)
