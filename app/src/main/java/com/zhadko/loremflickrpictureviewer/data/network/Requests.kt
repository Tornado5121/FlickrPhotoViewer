package com.zhadko.loremflickrpictureviewer.data.network

import com.zhadko.loremflickrpictureviewer.models.jsonModels.FlickrPhotoNetwork
import retrofit2.http.GET
import retrofit2.http.Query

interface Requests {

    @GET("api/1")
    suspend fun getPost(
        @Query("token") authToken: String,
        @Query("width") photoWidth: String,
        @Query("height") photoHeight: String,
        @Query("format") responseFormat: String,
        @Query("tag") photoTag: String
    ): FlickrPhotoNetwork

}