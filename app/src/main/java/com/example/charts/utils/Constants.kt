package com.example.charts.utils

import android.graphics.Color

class Constants {
    companion object {
        const val weeklyPath = "responseQuotesWeek.json"
        const val monthlyPath = "responseQuotesMonth.json"

        const val customBindingError = "Fragment views are destroyed - can't get bindings"

        val lineChartColors = intArrayOf(
            Color.rgb(1, 37, 58),
            Color.rgb(20, 118, 144),
            Color.rgb(252, 154, 0)
        )
    }
}