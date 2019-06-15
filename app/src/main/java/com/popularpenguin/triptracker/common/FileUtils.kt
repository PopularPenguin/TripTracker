package com.popularpenguin.triptracker.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object FileUtils {

    fun getPhotoFile(context: Context): File {
        val filesDir = context.filesDir
        val fileName = "${System.currentTimeMillis()}.jpg"

        return File(filesDir, fileName)
    }

    fun getPhotoUri(context: Context, photoFile: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "com.popularpenguin.triptracker.fileprovider",
            photoFile
        )
    }

    fun deletePhoto(activity: Activity, photoUri: Uri) {
        activity.grantUriPermission(activity.packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        activity.contentResolver
            .delete(photoUri, null, null) // TODO: Not deleting from gallery
        activity.revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
}