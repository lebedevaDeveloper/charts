package com.example.charts.utils

import android.util.Log
import com.example.charts.BuildConfig

object Logger {
    fun d(tag: String, message: Any) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message.toString())
    }
}