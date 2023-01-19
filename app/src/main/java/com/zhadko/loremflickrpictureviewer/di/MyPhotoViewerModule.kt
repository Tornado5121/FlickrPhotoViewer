package com.zhadko.loremflickrpictureviewer.di

import com.zhadko.loremflickrpictureviewer.data.database.FlickrPhotoDatabase
import com.zhadko.loremflickrpictureviewer.data.database.FlickrPhotoDatabaseRepoImpl
import com.zhadko.loremflickrpictureviewer.data.network.FlickrPhotoFetcherImpl
import com.zhadko.loremflickrpictureviewer.data.network.RetrofitClient
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.FlickrPhotoDatabaseRepo
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.FlickrPhotoFetcher
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.PhotoRepository
import com.zhadko.loremflickrpictureviewer.data.repositories.flickrPhotoRepository.PhotoRepositoryImpl
import com.zhadko.loremflickrpictureviewer.data.repositories.loadingRepository.LoadingRepository
import com.zhadko.loremflickrpictureviewer.data.repositories.loadingRepository.LoadingRepositoryImpl
import com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen.DetailedPhotoViewModel
import com.zhadko.loremflickrpictureviewer.ui.photoListScreen.PhotoListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single<LoadingRepository> { LoadingRepositoryImpl(androidContext()) }
    single<FlickrPhotoDatabaseRepo> {
        FlickrPhotoDatabaseRepoImpl(
            FlickrPhotoDatabase.getDatabaseInstance(
                androidContext()
            ).flickrPhotoDao
        )
    }
    single<FlickrPhotoFetcher> { FlickrPhotoFetcherImpl(RetrofitClient.api) }
    single<PhotoRepository> { PhotoRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
    viewModel { PhotoListViewModel(get()) }
    viewModel { (id: String) -> DetailedPhotoViewModel(id, get(), get()) }
}