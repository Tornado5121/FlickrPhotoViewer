package com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.PhotoRepository
import com.zhadko.loremflickrpictureviewer.data.repositories.loadingRepository.LoadingRepository
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailedPhotoViewModel(
    private val id: String,
    private val photoRepository: PhotoRepository,
    private val loadingRepository: LoadingRepository
) : ViewModel() {

    private val mErrorMessageLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = mErrorMessageLiveData

    private val mFlickrPhotoLiveData = MutableLiveData<FlickrPhoto>()
    val flickrPhotoLiveData: LiveData<FlickrPhoto> = mFlickrPhotoLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loadingRepository.errorFlow.collectLatest {
                mErrorMessageLiveData.postValue(it)
            }
        }
    }

    fun getPhotoByFileName() {
        viewModelScope.launch(Dispatchers.IO) {
            mFlickrPhotoLiveData.postValue(photoRepository.getPhotoByFileName(id))
        }
    }

    fun savePhotoToGallery(photoUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loadingRepository.downloadPhotoToGallery(photoUrl)
        }
    }

}