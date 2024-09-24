package com.example.photogallery.data

import com.example.photogallery.data.interceptors.SupportInterceptor
import com.example.photogallery.data.interceptors.TimberLoggingInterceptor
import com.example.photogallery.network.PexelsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface AppContainer {
    val photosRepository: PhotosRepository
}

class DefaultAppContainer : AppContainer {

    private val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    )

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(TimberLoggingInterceptor())
        .addInterceptor(SupportInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(AppConst.baseUrl)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()

    private val retrofitService: PexelsApiService by lazy {
        retrofit.create(PexelsApiService::class.java)
    }

    override val photosRepository: PhotosRepository by lazy {
        NetworkPhotosRepository(retrofitService)
    }
}

object AppConst {
    const val baseUrl = "https://api.pexels.com/v1/"
    const val curatedPhotos = "curated"
    const val pexelsStartingIndexPage = 1
    const val networkPageSize = 10
}