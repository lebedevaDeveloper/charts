package com.example.charts.networking

import android.content.Context
import com.example.charts.networking.NetworkingConstants.Companion.BASE_URL
import com.example.charts.networking.NetworkingConstants.Companion.contentTypeKey
import com.example.charts.networking.NetworkingConstants.Companion.contentTypeValue
import com.example.charts.networking.NetworkingConstants.Companion.successCode
import com.example.charts.networking.NetworkingConstants.Companion.weekQuotesEndpoint
import com.example.charts.utils.Constants.Companion.monthlyPath
import com.example.charts.utils.Constants.Companion.weeklyPath
import com.example.charts.utils.getJsonFromFile
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class ChartsInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toUri().toString()

        // check the endpoint of the "request" to mock right data
        val response = if (uri.endsWith(weekQuotesEndpoint)) {
            context.getJsonFromFile(weeklyPath)
        } else {
            context.getJsonFromFile(monthlyPath)
        }

        return chain.proceed(chain.request().newBuilder().url(BASE_URL).build())
            .newBuilder()
            .code(successCode)
            .protocol(Protocol.HTTP_2)
            .message(response)
            .body(
                response.toByteArray().toResponseBody(contentTypeValue.toMediaTypeOrNull())
            )
            .addHeader(contentTypeKey, contentTypeValue)
            .build()
    }

}