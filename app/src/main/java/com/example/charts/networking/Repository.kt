package com.example.charts.networking

import com.example.charts.models.Stock

/**
 * Triggers the api and emits data to viewModel
 */
class Repository(private val api: ChartsAPI) {

    suspend fun getWeeklyData(): Result<Stock> {
        return try {
            val response = api.getQuotesForWeek()
            if (response.isSuccessful) {
                response.handleSuccess()
            } else {
                response.handleApiError()
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getMonthlyData(): Result<Stock> {
        return try {
            val response = api.getQuotesForMonth()
            if (response.isSuccessful) {
                response.handleSuccess()
            } else {
                response.handleApiError()
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}