package com.zhadko.loremflickrpictureviewer.data.repositories

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

interface FlickrPhotoFetcher {

    suspend fun getFlickrPhoto() : FlickrPhoto

}