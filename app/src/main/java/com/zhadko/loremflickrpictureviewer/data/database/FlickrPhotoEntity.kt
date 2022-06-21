package com.zhadko.loremflickrpictureviewer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

@Entity
data class FlickrPhotoEntity(
    @PrimaryKey
    val photoName: String,
    val authorName: String
)

fun FlickrPhotoEntity.asDomainModel(): FlickrPhoto {
    return FlickrPhoto(
        file = photoName,
        owner = authorName
    )
}

fun List<FlickrPhotoEntity>.asDomainModel(): List<FlickrPhoto> {
    return map {
        it.asDomainModel()
    }
}