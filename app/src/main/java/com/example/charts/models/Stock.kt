package com.example.charts.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  Stock (val content: StockData, val status: String)