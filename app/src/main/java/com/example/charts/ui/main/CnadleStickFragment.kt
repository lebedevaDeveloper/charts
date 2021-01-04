package com.example.charts.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.charts.R
import com.example.charts.databinding.FragmentCandlestickChartBinding
import com.example.charts.ui.fragmentBinding
import com.github.mikephil.charting.data.CandleData
import org.koin.android.viewmodel.ext.android.viewModel

class CandleStickFragment : Fragment(R.layout.fragment_candlestick_chart) {
    private val binding by fragmentBinding(FragmentCandlestickChartBinding::bind)

    private val chartsViewModel: ChartsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChartViews()
        setUpUICallbacks()
        setupObservers()
        chartsViewModel.getWeeklyDataFromRepository(DataType.CANDLESTICK)
    }

    private fun setUpUICallbacks() {
        chartsViewModel.requestInProgress.observe(viewLifecycleOwner, Observer { inProgress ->
            if (inProgress) {
                binding.progress.showView()
                binding.chartsContainer.hideView()
            } else {
                binding.progress.hideView()
                binding.chartsContainer.showView()
            }
        })

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.week_radio -> chartsViewModel.getWeeklyDataFromRepository(DataType.CANDLESTICK)
                R.id.month_radio -> chartsViewModel.getMonthlyDataFromRepository(DataType.CANDLESTICK)
            }
        }

        chartsViewModel.showError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupChartViews(){
        binding.chart1.setupChartView()
        binding.chart2.setupChartView()
        binding.chart3.setupChartView()
    }

    private fun setupObservers() {
        chartsViewModel.candlesticksChartData.observe(viewLifecycleOwner, Observer { data ->
            setupCandleStickView(data)
        })
    }

    private fun setupCandleStickView(data: MutableList<CandleData>?) {
        if(!data.isNullOrEmpty()){
            binding.chart1.data = data[0]
            if(data.size > 1){
                binding.chart2.data = data[1]
            }
            if(data.size > 2){
                binding.chart3.data = data[2]
            }
        }
    }

}