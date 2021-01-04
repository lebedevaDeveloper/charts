package com.example.charts.utils

import android.content.Context

/**
 * Reads file contents from assets folder and represents it in a string form
 */
fun Context.getJsonFromFile(fileName: String) : String {
    val inputStream = assets.open(fileName)
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    inputStream.close()
    return String(buffer, Charsets.UTF_8)
}