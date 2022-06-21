package com.zhadko.loremflickrpictureviewer.data.repositories.loadingRepository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import android.provider.MediaStore
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class LoadingRepositoryImpl(
    private val context: Context
) : LoadingRepository {

    private val mErrorFlow = MutableStateFlow("")
    override val errorFlow: StateFlow<String> = mErrorFlow

    private fun getBitmap(imageUrl: String): Bitmap {
        return Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .submit()
            .get()
    }

    override suspend fun downloadPhotoToGallery(imageUrl: String) {
        val resolver = context.contentResolver

        val imageCollection: Uri = if (Build.VERSION.SDK_INT >= Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "MyName" + ".jpg")
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        val imageUri = resolver.insert(imageCollection, contentValues)

        try {
            val outputStream = Objects.requireNonNull(imageUri)
                ?.let { resolver.openOutputStream(it) }
            val bitMap = getBitmap(imageUrl)
            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Objects.requireNonNull(outputStream)
            mErrorFlow.value = "Photo is downloaded"
        } catch (e: Exception) {
            e.printStackTrace()
            mErrorFlow.value = "Sorry, something went wrong,please, try again later"
        }
    }

}