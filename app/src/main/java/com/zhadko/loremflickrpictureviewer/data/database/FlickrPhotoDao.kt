package com.zhadko.loremflickrpictureviewer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FlickrPhotoDao {

    @Query("SELECT * from FlickrPhotoEntity")
    fun getPhotoList(): List<FlickrPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPhotoList(photoList: List<FlickrPhotoEntity>)

    @Query("DELETE from FlickrPhotoEntity")
    fun clearPhotoDatabase()

    @Query("SELECT * from FlickrPhotoEntity where photoName = :photoName")
    fun getPhotoByName(photoName: String): FlickrPhotoEntity

}