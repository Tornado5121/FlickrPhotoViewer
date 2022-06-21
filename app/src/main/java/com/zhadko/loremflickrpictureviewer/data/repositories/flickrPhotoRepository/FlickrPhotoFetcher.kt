package com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

interface FlickrPhotoFetcher {

    suspend fun getFlickrPhoto(photoTag: String) : FlickrPhoto

}