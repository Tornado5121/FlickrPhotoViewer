package com.zhadko.loremflickrpictureviewer.models.domainModels

import com.zhadko.loremflickrpictureviewer.data.database.FlickrPhotoEntity

data class FlickrPhoto(
    val file: String,
    val owner: String
)

fun FlickrPhoto.asDatabaseModel(): FlickrPhotoEntity {
    return FlickrPhotoEntity(
        photoName = file,
        authorName = owner
    )
}

fun List<FlickrPhoto>.asDatabaseModel(): List<FlickrPhotoEntity> {
    return map {
        it.asDatabaseModel()
    }
}
