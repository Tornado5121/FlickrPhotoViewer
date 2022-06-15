package com.zhadko.loremflickrpictureviewer.models.jsonModels

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

data class FlickrPhotoNetwork(
    val file: String,
    val filter: String,
    val height: Int,
    val license: String,
    val owner: String,
    val tagMode: String,
    val tags: String,
    val width: Int
)

fun FlickrPhotoNetwork.asFlickrPhotoDomain(): FlickrPhoto {
    return FlickrPhoto(file, owner)
}