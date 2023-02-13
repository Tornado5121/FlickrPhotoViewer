package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.PhotoRepository
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _searchTag = MutableStateFlow("sea")

    private val _myPhotoList = MutableStateFlow<List<FlickrPhoto>>(emptyList())
    val myPhotoList = _myPhotoList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _myPhotoList.update { photoRepository.getPhotoList(_searchTag.value) }
        }
    }

    fun getNextPagePhotosList() {
        viewModelScope.launch(Dispatchers.IO) {
            val photoList = photoRepository.getPhotoList(_searchTag.value)
            val currentList = _myPhotoList.value
            _myPhotoList.emit(currentList + photoList)
        }
    }

    fun searchPhotosByTag(photoTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setSearchTag(photoTag)
            val photoList = photoRepository.getPhotoList(photoTag)
            _myPhotoList.update { photoList }
        }
    }

    fun setSearchTag(photoTag: String) {
        if (photoTag.isNotEmpty()) {
            _searchTag.update { photoTag }
        }
    }

}