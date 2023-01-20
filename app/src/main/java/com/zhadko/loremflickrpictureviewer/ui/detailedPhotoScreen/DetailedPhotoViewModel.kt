package com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.PhotoRepository
import com.zhadko.loremflickrpictureviewer.data.repositories.loadingRepository.LoadingRepository
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailedPhotoViewModel(
    private val id: String,
    private val photoRepository: PhotoRepository,
    private val loadingRepository: LoadingRepository
) : ViewModel() {

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _flickrPhoto = MutableSharedFlow<FlickrPhoto>()
    val flickrPhoto = _flickrPhoto.asSharedFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _errorMessage.emit(loadingRepository.errorFlow.value)
        }
    }

    fun getPhotoByFileName() {
        viewModelScope.launch(Dispatchers.IO) {
            _flickrPhoto.emit(photoRepository.getPhotoByFileName(id))
        }
    }

    fun savePhotoToGallery(photoUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loadingRepository.downloadPhotoToGallery(photoUrl)
        }
    }

}