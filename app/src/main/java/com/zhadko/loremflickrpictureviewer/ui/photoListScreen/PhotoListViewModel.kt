package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.PhotoRepository
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val mMyPhotoListLiveData = MutableLiveData<List<FlickrPhoto>>()
    val myPhotoListLiveData: LiveData<List<FlickrPhoto>> = mMyPhotoListLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            mMyPhotoListLiveData.postValue(photoRepository.getPhotoList())
        }
    }

    fun getNextPagePhotosList() {
        viewModelScope.launch(Dispatchers.IO) {
            val photoList = photoRepository.getPhotoList()
            val currentList = mMyPhotoListLiveData.value ?: listOf()
            mMyPhotoListLiveData.postValue(currentList + photoList)
        }
    }

    fun searchPhotosByTag(photoTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            photoRepository.setSearchTag(photoTag)
            val photoList = photoRepository.getPhotoList()
            mMyPhotoListLiveData.postValue(photoList)
        }
    }

}