package com.example.charts.networking

import com.example.charts.utils.Logger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import java.io.IOException

/**
 * Represents a wrapper for the coroutines and retrofit default response
 */
fun Response<*>.parseError(): RetrofitError {

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val parser = moshi.adapter( RetrofitError::class.java)

    var error =  RetrofitError("")
    try {
        error = parser.fromJson(errorBody()?.string() ?: "") as RetrofitError
    } catch (e: IOException) {
        e.message?.let { Logger.d("RetrofitError", it) }
    }
    return  error
}

// will be eventually sent to observer as an api response
fun <T> Response<T>.handleApiError(): Result.Error {
    val error = parseError()
    return Result.Error(Exception(error.message))
}

fun <T> Response<T>.handleSuccess(): Result<T> {
   body()?.let {
        return Result.Success(it)
    } ?: return handleApiError()
}