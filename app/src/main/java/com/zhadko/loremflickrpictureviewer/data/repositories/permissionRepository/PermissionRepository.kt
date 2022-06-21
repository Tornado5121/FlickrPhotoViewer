package com.zhadko.loremflickrpictureviewer.data.repositories.permissionRepository

interface PermissionRepository {

    fun isStoragePermissionsGranted() : Boolean

}