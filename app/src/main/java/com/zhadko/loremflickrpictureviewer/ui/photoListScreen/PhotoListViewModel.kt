package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhadko.loremflickrpictureviewer.data.repositories.PhotoRepository
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoListViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val mMyPhotoListLiveData = MutableLiveData<List<FlickrPhoto>>()
    val myPhotoListLiveData: LiveData<List<FlickrPhoto>> = mMyPhotoListLiveData
    var photoList: List<FlickrPhoto> = listOf()

    fun getPhotoList() {
        viewModelScope.launch(Dispatchers.IO) {
            if (photoList.isEmpty()) {
                photoList = photoRepository.getPhotoList()
                mMyPhotoListLiveData.postValue(photoList)
            }
        }
    }

}