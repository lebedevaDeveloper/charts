package com.example.charts.networking

import com.example.charts.models.Stock
import retrofit2.Response
import retrofit2.http.GET

interface ChartsAPI {
    @GET("get/weekly_quotes")
    suspend fun getQuotesForWeek(): Response<Stock>

    @GET("get/monthly_quotes")
    suspend fun getQuotesForMonth(): Response<Stock>
}