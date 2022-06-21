package com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

interface FlickrPhotoDatabaseRepo {

    suspend fun getPhotoList(): List<FlickrPhoto>
    suspend fun addPhotoList(photoList: List<FlickrPhoto>)
    suspend fun clearDatabase()
    suspend fun getPhotoByName(photoName: String) : FlickrPhoto

}