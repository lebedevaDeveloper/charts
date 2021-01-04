package com.example.charts.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.charts.models.QuoteSymbol
import com.example.charts.models.Stock
import com.example.charts.models.StockPerformance
import com.example.charts.networking.Repository
import com.example.charts.networking.Result
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.launch
import java.util.*

class ChartsViewModel(private val repository: Repository) : ViewModel() {

    val requestInProgress: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val showError: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    val candlesticksChartData: MutableLiveData<MutableList<CandleData>?> by lazy {
        MutableLiveData<MutableList<CandleData>?>()
    }

    val lineChartData: MutableLiveData<LineData> by lazy {
        MutableLiveData<LineData>()
    }

    fun getWeeklyDataFromRepository(dataType: DataType) {
        requestInProgress.value = true
        viewModelScope.launch {
            val result = repository.getWeeklyData()
            requestInProgress.value = false
            when (result) {
                is Result.Success -> {
                    when (dataType) {
                        DataType.LINE -> getLineChartValues(result.successData)
                        DataType.CANDLESTICK -> getCandleStickChartValues(result.successData)
                    }
                }
                is Result.Error -> showError.value = result.exception.message
            }
        }
    }


    fun getMonthlyDataFromRepository(dataType: DataType) {
        requestInProgress.value = true
        viewModelScope.launch {
            val result = repository.getMonthlyData()
            requestInProgress.value = false
            when (result) {
                is Result.Success -> {
                    when (dataType) {
                        DataType.LINE -> getLineChartValues(result.successData)
                        DataType.CANDLESTICK -> getCandleStickChartValues(result.successData)
                    }
                }
                is Result.Error -> showError.value = result.exception.message
            }
        }
    }

    private fun getLineChartValues(data: Stock?) {
        // needed for different lines colors
        var mapEntryIndex = 0
        val dataSets: MutableList<ILineDataSet>? = data?.content?.quoteSymbols?.map { symbol ->
            val performance = getPerformance(symbol)
            val entries =
                performance.map { Entry(it.timeStamp.toFloat(), it.performance.toFloat()) }
            val d = LineDataSet(entries, symbol.symbol)

            d.setupChartView(mapEntryIndex)

            mapEntryIndex++
            return@map d

        }?.toMutableList()

        lineChartData.value = LineData(dataSets)
    }


    private fun getCandleStickChartValues(data: Stock?) {
        candlesticksChartData.value = data?.content?.quoteSymbols?.map { symbol ->
            val values = ArrayList<CandleEntry>()
            symbol.timestamps.forEachIndexed { index, _ ->
                values.add(getCandleEntry(symbol, index))
            }
            val set = CandleDataSet(values, symbol.symbol)

            set.setupChartView()

            return@map CandleData(set)
        }?.toMutableList()
    }

    /**
     * Parses json to the chart data - gets performance for line chart
     * @param symbol - data from the response
     */
    private fun getPerformance(symbol: QuoteSymbol): MutableList<StockPerformance> {
        if (!symbol.opens.isNullOrEmpty()) {
            val price = symbol.opens[0]
            return symbol.timestamps.zip(symbol.opens).map { opensData ->
                val stockPerformanceValue = getPerformanceValue(opensData.second, price)
                return@map StockPerformance(stockPerformanceValue, opensData.first)
            }.toMutableList()
        } else {
            return mutableListOf()
        }
    }

    private  fun getPerformanceValue(openValue: Double, stockPrice: Double) : Double{
        return 100 * openValue / stockPrice - 100
    }

    /**
     * Parses json to the chart data
     * @param symbol - data from the response
     * @param i - index of the stock data timestamps
     */
    private fun getCandleEntry(symbol: QuoteSymbol, i: Int): CandleEntry {
        val closes = symbol.closures[i]
        val highs = symbol.highs[i]
        val opens = symbol.opens[i]
        val lows = symbol.lows[i]

        return CandleEntry(
            i.toFloat(),
            highs.toFloat(),
            lows.toFloat(),
            opens.toFloat(),
            closes.toFloat()
        )
    }
}

enum class DataType {
    LINE, CANDLESTICK
}