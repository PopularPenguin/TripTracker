package com.popularpenguin.triptracker.common

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {

    fun getPhotoFile(context: Context): File {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile(System.currentTimeMillis().toString(), ".jpg", filesDir)

        tempFile.deleteOnExit()

        return tempFile
    }

    fun getPhotoUri(context: Context, photoFile: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "com.popularpenguin.triptracker.fileprovider",
            photoFile
        )
    }

    fun deletePhoto(context: Context, photoUri: Uri) {
        Log.d("FileUtils", "Authority = ${photoUri.authority}, Path = ${photoUri.path}, $photoUri")

        val isDeleted = context.contentResolver.delete(photoUri, null, null)

        Log.d("FileUtils", "is deleted? $isDeleted")
    }
}