package com.zhadko.loremflickrpictureviewer.data.repositories

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

class PhotoRepositoryImpl(
    private val flickrPhotoFetcher: FlickrPhotoFetcher
) : PhotoRepository {

    private lateinit var photoList: List<FlickrPhoto>

    override suspend fun getPhotoList(): List<FlickrPhoto> {
        photoList = (0 until 30).map {
            flickrPhotoFetcher.getFlickrPhoto()
        }
        return photoList

    }

    override suspend fun getPhotoByFileName(
        fileName: String
    ): FlickrPhoto? {
        return photoList.find {
            it.file == fileName
        }
    }

}