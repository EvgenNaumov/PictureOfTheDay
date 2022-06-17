package com.example.pictureoftheday.viewmodel

import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseDate
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseListDate

sealed class PictureOfTheEarthAppState {
    data class Success(val pictureOfTheEarthResponseData: PictureOfTheEarthResponseListDate):PictureOfTheEarthAppState()
    data class Error(val error:String):PictureOfTheEarthAppState()
    data class Loading(val progress:Int?):PictureOfTheEarthAppState()
}