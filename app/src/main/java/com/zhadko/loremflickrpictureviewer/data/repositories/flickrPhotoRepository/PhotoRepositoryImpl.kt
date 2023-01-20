package com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository

import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

class PhotoRepositoryImpl(
    private val flickrPhotoFetcher: FlickrPhotoFetcher,
    private val flickrPhotoDatabaseRepo: FlickrPhotoDatabaseRepo
) : PhotoRepository {

    private var isCachedDataExistOrNeeded = false

    override suspend fun getPhotoList(searchQuery: String): List<FlickrPhoto> {
        return try {
            val photoList = (0 until 30).map {
                flickrPhotoFetcher.getFlickrPhoto(searchQuery)
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

    override suspend fun getPhotoByFileName(
        fileName: String
    ): FlickrPhoto {
        return flickrPhotoDatabaseRepo.getPhotoByName(fileName)
    }

}

