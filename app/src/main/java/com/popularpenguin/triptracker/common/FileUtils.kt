package com.popularpenguin.triptracker.common

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {

    fun getPhotoFile(context: Context): File {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(System.currentTimeMillis().toString(), ".jpg", filesDir)
    }

    fun getPhotoUri(context: Context, photoFile: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "com.popularpenguin.triptracker.fileprovider",
            photoFile
        )
    }

    fun deletePhoto(context: Context, photoUri: Uri) {
        context.contentResolver.delete(photoUri, null, null)
    }
}