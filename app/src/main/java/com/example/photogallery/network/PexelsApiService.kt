package com.example.photogallery.network

import com.example.photogallery.data.AppConst
import com.example.photogallery.model.MainResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelsApiService {

        @GET(AppConst.curatedPhotos)
        suspend fun getCuratedPhotos(
                @Query("page") page: Int,
                @Query("per_page") perPage: Int
        ): MainResponse
}
