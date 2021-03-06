package com.example.pictureoftheday.viewmodel

import com.example.pictureoftheday.repository.systemsolar.PictureOfTheSystemResponseDate

sealed class PictureOfTheSystemAppState{
    data class Success(val pictureOfTheSystemsResponseData: List<PictureOfTheSystemResponseDate>):PictureOfTheSystemAppState()
    data class Error(val error:String):PictureOfTheSystemAppState()
    data class Loading(val progress:Int?):PictureOfTheSystemAppState()
}
