package com.example.charts.ui.main

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.showView() {
    visibility = VISIBLE
}

fun View.hideView() {
    visibility = INVISIBLE
}