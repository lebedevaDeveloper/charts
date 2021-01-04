package com.example.charts.modules

import android.content.Context
import com.example.charts.networking.ChartsAPI
import com.example.charts.networking.ChartsInterceptor
import com.example.charts.networking.NetworkingConstants.Companion.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    val connectTimeout: Long = 50
    val readTimeout: Long = 50

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // here we set up a retrofit for api
    fun provideRetrofit(client: OkHttpClient, log: Boolean = true): Retrofit =
        Retrofit.Builder().apply {
            client(client)
            baseUrl(BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()

    // here we set up a client for retrofit with custom timeout, interceptor and logger
    fun provideHttpClient(log: Boolean = true, interceptor: Interceptor? = null): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(connectTimeout, TimeUnit.SECONDS)
            interceptor?.let { interc ->
                addInterceptor(interc)
            }
            readTimeout(readTimeout, TimeUnit.SECONDS)
            if (log) {
                val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(httpLoggingInterceptor)
            }
        }.build()

    // here we set up mock interceptor where data from files is presented as response data
    fun provideInterceptor(context: Context): Interceptor {
        return ChartsInterceptor(context)
    }

    single { provideInterceptor(androidContext()) }

    single { provideHttpClient(true, get()) }

    single {
        provideRetrofit(get())
    }
}


val apiModule = module {
    // here we set the api itself which will be used by repository
    fun provideCountriesApi(retrofit: Retrofit): ChartsAPI {
        return retrofit.create(ChartsAPI::class.java)
    }
    single { provideCountriesApi(get()) }
}

