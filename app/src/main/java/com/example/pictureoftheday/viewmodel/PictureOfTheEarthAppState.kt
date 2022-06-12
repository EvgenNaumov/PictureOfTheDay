package com.example.pictureoftheday.viewmodel

import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseDate

sealed class PictureOfTheEarthAppState {
    data class Success(val pictureOfTheEarthResponseData: PictureOfTheEarthResponseDate):PictureOfTheEarthAppState()
    data class Error(val error:String):PictureOfTheEarthAppState()
    data class Loading(val progress:Int?):PictureOfTheEarthAppState()
}