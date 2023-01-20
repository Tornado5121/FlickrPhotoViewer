package com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

interface PhotoRepository {

    suspend fun getPhotoList(searchQuery: String): List<FlickrPhoto>
    suspend fun getPhotoByFileName(fileName: String): FlickrPhoto?

}