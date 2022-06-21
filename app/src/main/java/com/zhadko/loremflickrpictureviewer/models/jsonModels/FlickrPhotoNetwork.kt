package com.zhadko.loremflickrpictureviewer.models.jsonModels

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

data class FlickrPhotoNetwork(
    val file: String,
    val owner: String
)

fun FlickrPhotoNetwork.asFlickrPhotoDomain(): FlickrPhoto {
    return FlickrPhoto(file, owner)
}