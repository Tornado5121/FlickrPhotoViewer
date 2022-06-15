package com.zhadko.loremflickrpictureviewer.data.repositories

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

interface PhotoRepository {

    suspend fun getPhotoList(): List<FlickrPhoto>
    suspend fun getPhotoByFileName(fileName: String): FlickrPhoto?

}