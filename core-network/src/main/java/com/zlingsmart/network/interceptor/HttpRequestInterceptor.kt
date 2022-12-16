package com.zlingsmart.network.interceptor

import android.util.Log
import com.zlingsmart.network.BuildConfig.DEBUG
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

internal class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val request = originRequest.newBuilder().url(originRequest.url).build()
        Timber.d(request.toString())
        return chain.proceed(request)
    }
}