package com.zhadko.loremflickrpictureviewer.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://loremflickr.com/"

object RetrofitClient {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val api: Requests by lazy { retrofit.create(Requests::class.java) }

}