package com.example.pictureoftheday.viewmodel

import com.example.pictureoftheday.repository.mars.PictureOfTheMarsResponseDate

sealed class PictureOfTheMarsAppState{
    data class Success(val pictureOfTheMarsResponseData: PictureOfTheMarsResponseDate):PictureOfTheMarsAppState()
    data class Error(val error:String):PictureOfTheMarsAppState()
    data class Loading(val progress:Int?):PictureOfTheMarsAppState()
}

