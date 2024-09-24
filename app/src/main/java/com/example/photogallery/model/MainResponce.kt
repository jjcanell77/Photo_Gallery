package com.example.photogallery.model

import com.squareup.moshi.Json

data class MainResponse(
    @Json(name = "next_page") val nextPage: String = "",
    val page: Int = 0,
    @Json(name = "per_page") val perPage: Int = 0,
    val photos: List<Photo> = emptyList(),
    @Json(name = "total_results") val totalResults: Int = 0
)