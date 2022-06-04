package com.example.pictureoftheday.viewmodel

import com.example.pictureoftheday.repository.PictureOfTheDayResponseDate

sealed class PictureOfTheDayAppState{
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseDate):PictureOfTheDayAppState()
    data class Error(val error:String):PictureOfTheDayAppState()
    data class Loading(val progress:Int?):PictureOfTheDayAppState()
}
