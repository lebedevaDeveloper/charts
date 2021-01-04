package com.example.charts.ui.main

import android.graphics.Color
import android.graphics.Paint
import com.example.charts.utils.Constants
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.LineDataSet

/**
 * Extensions responsible for charts appearance
 */

fun LineDataSet.setupChartView(index: Int){
    val lineColor: Int = Constants.lineChartColors[index % Constants.lineChartColors.size]
    color = lineColor

    lineWidth = 2f
    circleRadius = 5f

    setCircleColor(lineColor)
}
fun LineChart.setupChartView() {

    setDrawBorders(false)
    setTouchEnabled(true)
    setPinchZoom(false)
    setScaleEnabled(true)

    description.isEnabled = false
    axisLeft.isEnabled = false
    axisRight.setDrawAxisLine(false)
    xAxis.setDrawAxisLine(false)

    isDragEnabled = false

    val l: Legend = legend
    l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    l.orientation = Legend.LegendOrientation.VERTICAL
    l.setDrawInside(false)
}

fun CandleDataSet.setupChartView() {
    setDrawIcons(false)
    axisDependency = YAxis.AxisDependency.LEFT
    decreasingColor = Color.rgb(190, 60, 0)
    decreasingPaintStyle = Paint.Style.FILL
    increasingColor = Color.rgb(112, 171, 73)
    increasingPaintStyle = Paint.Style.FILL
    neutralColor = Color.BLUE
    shadowColor = Color.DKGRAY
    shadowWidth = 0.5f
}

fun CandleStickChart.setupChartView() {
    description.isEnabled = false

    setMaxVisibleValueCount(300)
    setScaleEnabled(true)
    setPinchZoom(true)

    isDragEnabled = true

    val leftAxis: YAxis = axisLeft
    leftAxis.setLabelCount(8, false)
    leftAxis.setDrawGridLines(false)
    leftAxis.setDrawAxisLine(false)

    val xAxis: XAxis = xAxis
    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.setDrawGridLines(false)

    val rightAxis: YAxis = axisRight
    rightAxis.isEnabled = false

    val l: Legend = legend
    l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
    l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
    l.orientation = Legend.LegendOrientation.VERTICAL
    l.setDrawInside(false)

}