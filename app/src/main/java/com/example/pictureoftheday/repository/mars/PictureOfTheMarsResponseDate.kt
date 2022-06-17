package com.example.pictureoftheday.repository.mars


import com.google.gson.annotations.SerializedName

data class PictureOfTheMarsResponseDate(
    @SerializedName("photos")
    val photos: List<Photo>
)