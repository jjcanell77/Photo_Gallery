package com.example.photogallery.model

import com.squareup.moshi.Json

data class Photo(
    val id: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    @Json(name = "photographer_url") val photographerUrl: String = "",
    @Json(name = "photographer_id") val photographerId: Int = 0,
    @Json(name = "avg_color") val avgColor: String = "",
    val src: Src = Src(),
    val liked: Boolean = false,
    val alt: String = ""
)