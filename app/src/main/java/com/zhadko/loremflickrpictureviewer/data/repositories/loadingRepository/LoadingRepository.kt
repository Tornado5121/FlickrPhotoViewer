package com.zhadko.loremflickrpictureviewer.data.repositories.loadingRepository

import kotlinx.coroutines.flow.StateFlow

interface LoadingRepository {

    val errorFlow: StateFlow<String>

    suspend fun downloadPhotoToGallery(imageUrl: String)

}