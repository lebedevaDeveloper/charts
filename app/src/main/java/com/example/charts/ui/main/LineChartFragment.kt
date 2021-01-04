package com.example.charts.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.charts.R
import com.example.charts.databinding.FragmentLineChartBinding
import com.example.charts.ui.fragmentBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.LineData
import org.koin.android.viewmodel.ext.android.viewModel

class LineChartFragment : Fragment(R.layout.fragment_line_chart) {

    private val binding by fragmentBinding(FragmentLineChartBinding::bind)

    private val chartsViewModel: ChartsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUICallbacks()
        setupObservers()

    }

    private  fun setUpUICallbacks() {
        chartsViewModel.requestInProgress.observe(viewLifecycleOwner, Observer { inProgress ->
           if(inProgress) {
               binding.progress.showView()
               binding.chart.hideView()
           } else{
               binding.progress.hideView()
               binding.chart.showView()
           }
        })

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.week_radio -> chartsViewModel.getWeeklyDataFromRepository(DataType.LINE)
                R.id.month_radio -> chartsViewModel.getMonthlyDataFromRepository(DataType.LINE)
            }
        }

        chartsViewModel.showError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onResume() {
        super.onResume()
        binding.radioGroup.check(R.id.week_radio)
        chartsViewModel.getWeeklyDataFromRepository(DataType.LINE)
    }

    private fun setupObservers() {
        chartsViewModel.lineChartData.observe(viewLifecycleOwner, Observer { data ->
            setupLineChartView(data)
        })
    }

    private fun setupLineChartView(data: LineData) {
        binding.chart.xAxis.labelRotationAngle = 0f

        binding.chart.data = data

        binding.chart.setupChartView()

        binding.chart.animateX(1800, Easing.EaseInExpo)
    }

}