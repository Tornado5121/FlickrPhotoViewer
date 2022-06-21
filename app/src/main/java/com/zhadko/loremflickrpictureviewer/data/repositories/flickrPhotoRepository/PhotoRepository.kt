package com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.flow.StateFlow

interface PhotoRepository {

    val mySearchFlow: StateFlow<String>

    suspend fun setSearchTag(photoTag: String)
    suspend fun getPhotoList(): List<FlickrPhoto>
    suspend fun getPhotoByFileName(fileName: String): FlickrPhoto?

}