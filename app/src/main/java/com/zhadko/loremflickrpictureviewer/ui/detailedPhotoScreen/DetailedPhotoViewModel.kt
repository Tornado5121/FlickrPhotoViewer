package com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhadko.loremflickrpictureviewer.data.repositories.PhotoRepository
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailedPhotoViewModel(
    private val id: String,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val mFlickrPhotoLiveData = MutableLiveData<FlickrPhoto>()
    val flickrPhotoLiveData: LiveData<FlickrPhoto> = mFlickrPhotoLiveData

    fun getPhotoByFileName() {
        viewModelScope.launch(Dispatchers.IO) {
            mFlickrPhotoLiveData.postValue(photoRepository.getPhotoByFileName(id))
        }
    }

}