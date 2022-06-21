package com.zhadko.loremflickrpictureviewer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.ui.photoListScreen.PhotoListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.my_container_view, PhotoListFragment())
                .commit()
        }
    }

}