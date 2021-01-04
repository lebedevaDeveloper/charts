package com.example.charts.modules

import com.example.charts.networking.ChartsAPI
import com.example.charts.networking.Repository
import org.koin.dsl.module

val repositoryModule = module {

    // here we setup the repository which triggers tha api requests
    fun provideCountryRepository(api: ChartsAPI): Repository {
        return Repository(api)
    }
    single { provideCountryRepository(get()) }

}