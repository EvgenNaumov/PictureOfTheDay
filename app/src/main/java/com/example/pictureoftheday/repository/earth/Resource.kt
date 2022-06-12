package com.example.pictureoftheday.repository.earth


import com.google.gson.annotations.SerializedName

data class Resource(
    @SerializedName("dataset")
    val dataset: String,
    @SerializedName("planet")
    val planet: String
)