package com.example.photogallery.data.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.IOException
import timber.log.Timber

class TimberLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        println("TimberLoggingInterceptor is firing for URL: ${request.url}")
        Timber.i(
            "Sending request %s on %s%n%s",
            request.url,
            chain.connection(),
            request.headers
        )
        val response: Response = chain.proceed(request)
        val responseBodyString = response.body?.string()

        // Ensure response is logged
        println("Response Body: $responseBodyString")

        val newResponse = response.newBuilder().body(
            responseBodyString?.toByteArray()?.toResponseBody(response.body?.contentType())
        ).build()

        val t2 = System.nanoTime()
        Timber.i(
            "Received response for %s in %.1fms%n%s",
            response.request.url,
            (t2 - t1) / 1e6,
            response.headers
        )

        return newResponse
    }
}