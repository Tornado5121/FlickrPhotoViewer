package com.zhadko.loremflickrpictureviewer.di

import com.zhadko.loremflickrpictureviewer.data.network.FlickrPhotoFetcherImpl
import com.zhadko.loremflickrpictureviewer.data.network.RetrofitClient
import com.zhadko.loremflickrpictureviewer.data.repositories.FlickrPhotoFetcher
import com.zhadko.loremflickrpictureviewer.data.repositories.PhotoRepository
import com.zhadko.loremflickrpictureviewer.data.repositories.PhotoRepositoryImpl
import com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen.DetailedPhotoViewModel
import com.zhadko.loremflickrpictureviewer.ui.photoListScreen.PhotoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<FlickrPhotoFetcher> { FlickrPhotoFetcherImpl(RetrofitClient.api) }
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { PhotoListViewModel(get()) }
    viewModel { (id: String) -> DetailedPhotoViewModel(id, get()) }
}