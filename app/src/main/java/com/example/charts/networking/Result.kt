package com.example.charts.networking


sealed class Result<out T> {

    data class Success<out T>(val successData : T) : Result<T>()
    class Error(val exception: java.lang.Exception, val message: String = exception.localizedMessage ?: "")
        : Result<Nothing>()
}

