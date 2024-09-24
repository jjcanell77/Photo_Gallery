package com.example.photogallery.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.photogallery.PhotoGalleryApplication
import com.example.photogallery.data.PhotosRepository
import com.example.photogallery.model.Photo
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val photosRepository: PhotosRepository) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<Photo>> = photosRepository.getCuratedPhotos()
        .cachedIn(viewModelScope)

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PhotoGalleryApplication

                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                    return HomeViewModel(application.container.photosRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
