package com.zhadko.loremflickrpictureviewer.data.network

import com.zhadko.loremflickrpictureviewer.data.repositories.FlickrPhotoFetcher
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import com.zhadko.loremflickrpictureviewer.models.jsonModels.asFlickrPhotoDomain

const val authToken = "2158.SPVCYmXnpeKnriIJiKcNRukeTtwjjamW"
const val photoWidth = "320"
const val photoHeight = "240"
const val responseFormat = "json"
const val photoTag = "sea"

class FlickrPhotoFetcherImpl(
    private val api: Requests
) : FlickrPhotoFetcher {

    override suspend fun getFlickrPhoto(): FlickrPhoto {
        return api.getPost(authToken, photoWidth, photoHeight, responseFormat, photoTag).asFlickrPhotoDomain()
    }

}