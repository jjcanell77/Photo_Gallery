package com.example.photogallery.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.photogallery.model.Photo
import com.example.photogallery.network.PexelsApiService
import retrofit2.HttpException
import java.io.IOException

class CuratedPhotosPagingSource(
    private val photosApiService: PexelsApiService
) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: AppConst.pexelsStartingIndexPage
        val loadSize = params.loadSize

        return try {
            println("Requesting page $position with load size $loadSize")
            val response = photosApiService.getCuratedPhotos(
                page = position,
                perPage = loadSize
            )
            val photos = response.photos

            val nextKey = if (photos.isEmpty()) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = photos,
                prevKey = if (position == AppConst.pexelsStartingIndexPage) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            println("IOException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            println("HttpException: ${e.message}")
            LoadResult.Error(e)
        }
    }
}