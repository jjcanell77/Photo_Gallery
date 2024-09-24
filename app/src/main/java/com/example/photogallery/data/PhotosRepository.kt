package com.example.photogallery.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.photogallery.model.Photo
import com.example.photogallery.network.PexelsApiService
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    fun getCuratedPhotos(): Flow<PagingData<Photo>>
}

class NetworkPhotosRepository(
    private val photosApiService: PexelsApiService
) : PhotosRepository {
    override fun getCuratedPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = AppConst.networkPageSize,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                CuratedPhotosPagingSource(photosApiService = photosApiService)
            }
        ).flow
    }
}