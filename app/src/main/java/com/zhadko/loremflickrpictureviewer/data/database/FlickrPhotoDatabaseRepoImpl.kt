package com.zhadko.loremflickrpictureviewer.data.database

import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.FlickrPhotoDatabaseRepo
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import com.zhadko.loremflickrpictureviewer.models.domainModels.asDatabaseModel

class FlickrPhotoDatabaseRepoImpl(
    private val flickrPhotoDao: FlickrPhotoDao
) : FlickrPhotoDatabaseRepo {

    override suspend fun addPhotoList(photoList: List<FlickrPhoto>) {
        flickrPhotoDao.addPhotoList(photoList.asDatabaseModel())
    }

    override suspend fun getPhotoList(): List<FlickrPhoto> {
        return flickrPhotoDao.getPhotoList().asDomainModel()
    }

    override suspend fun clearDatabase() {
        flickrPhotoDao.clearPhotoDatabase()
    }

    override suspend fun getPhotoByName(photoName: String): FlickrPhoto {
        return flickrPhotoDao.getPhotoByName(photoName).asDomainModel()
    }

}