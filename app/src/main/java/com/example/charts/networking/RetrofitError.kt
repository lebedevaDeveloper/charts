package com.example.charts.networking

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitError(val message: String)