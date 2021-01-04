package com.example.charts.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  StockData (val quoteSymbols: List<QuoteSymbol>)