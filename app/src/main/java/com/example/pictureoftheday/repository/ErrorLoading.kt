package com.example.pictureoftheday.repository

class ErrorLoading(errorMessage:String=""): Throwable() {
    private val _errorMessage = errorMessage
    override fun toString(): String {
        return _errorMessage
    }
}