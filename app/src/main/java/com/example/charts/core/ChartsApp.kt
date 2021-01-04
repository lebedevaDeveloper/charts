package com.example.charts.core

import android.app.Application
import com.example.charts.modules.apiModule
import com.example.charts.modules.repositoryModule
import com.example.charts.modules.retrofitModule
import com.example.charts.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChartsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ChartsApp)
            modules(
                apiModule,
                retrofitModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}
