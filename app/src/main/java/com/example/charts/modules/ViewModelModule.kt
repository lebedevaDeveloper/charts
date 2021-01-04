package com.example.charts.modules

import com.example.charts.ui.main.ChartsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    // here we setup the view model for our chart fragments
    viewModel {
        ChartsViewModel(repository = get())
    }
}
