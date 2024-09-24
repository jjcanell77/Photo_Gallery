package com.example.photogallery.data.interceptors

import okhttp3.*
import com.example.photogallery.BuildConfig

class SupportInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val apiKey = "RXl2OsaxNK75UaRRrdqOJsBGPGG3thNRKchGlEiYgyi8T3IQB9E7URwy"

        val newRequest = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", apiKey)
            .build()

        return chain.proceed(newRequest)
    }
}