package com.zhadko.loremflickrpictureviewer

import android.app.Application
import com.zhadko.loremflickrpictureviewer.di.dataModule
import com.zhadko.loremflickrpictureviewer.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(dataModule, viewModelModule))
        }
    }

}