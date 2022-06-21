package com.zhadko.loremflickrpictureviewer.data.repositories.permissionRepository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionRepositoryImpl(
    private val context: Context
) : PermissionRepository {

    override fun isStoragePermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).toString()
        ) == PackageManager.PERMISSION_GRANTED
    }

}