package com.zhadko.loremflickrpictureviewer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FlickrPhotoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FlickrPhotoDatabase : RoomDatabase() {

    abstract val flickrPhotoDao: FlickrPhotoDao

    companion object {

        @Volatile
        private var INSTANCE: FlickrPhotoDatabase? = null

        fun getDatabaseInstance(context: Context): FlickrPhotoDatabase {
            val databaseInstance = INSTANCE
            return if (databaseInstance != null) {
                databaseInstance
            } else {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlickrPhotoDatabase::class.java,
                    "flickrPhotoDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}