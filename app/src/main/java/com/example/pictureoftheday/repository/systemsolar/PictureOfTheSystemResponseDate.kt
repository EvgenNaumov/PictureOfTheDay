package com.example.pictureoftheday.repository.systemsolar

import com.google.gson.annotations.SerializedName

data class PictureOfTheSystemResponseDate(
    val flrID: String,
    val beginTime: String,
    val peakTime: String,
    val endTime: Any? = null,
    val classType: String,
    val sourceLocation: String,
    val activeRegionNum: Long,
    val link: String
)
