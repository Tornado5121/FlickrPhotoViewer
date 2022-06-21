package com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PhotoRepositoryImpl(
    private val flickrPhotoFetcher: FlickrPhotoFetcher,
    private val flickrPhotoDatabaseRepo: FlickrPhotoDatabaseRepo
) : PhotoRepository {

    private val mMySearchFlow = MutableStateFlow("sea")
    override val mySearchFlow: StateFlow<String> = mMySearchFlow

    private var isCachedDataExistOrNeeded = false

    override suspend fun getPhotoList(): List<FlickrPhoto> {
        return try {
            val photoList = (0 until 30).map {
                flickrPhotoFetcher.getFlickrPhoto(mMySearchFlow.value)
            }

            if (!isCachedDataExistOrNeeded) {
                flickrPhotoDatabaseRepo.clearDatabase()
                isCachedDataExistOrNeeded = true
            }

            flickrPhotoDatabaseRepo.addPhotoList(photoList)
            photoList
        } catch (e: Exception) {
            e.printStackTrace()
            flickrPhotoDatabaseRepo.getPhotoList()
        }
    }

    override suspend fun setSearchTag(photoTag: String) {
        if (photoTag.isNotEmpty()) {
            mMySearchFlow.value = photoTag
        }
    }

    override suspend fun getPhotoByFileName(
        fileName: String
    ): FlickrPhoto {
        return flickrPhotoDatabaseRepo.getPhotoByName(fileName)
    }

}

