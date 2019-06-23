package com.popularpenguin.triptracker.common

import android.app.Activity
import android.content.Context
import android.content.Intent
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

    fun deletePhoto(activity: Activity, photoUri: Uri) {
        activity.grantUriPermission(activity.packageName, photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        activity.contentResolver
            .delete(photoUri, null, null) // TODO: Not deleting from gallery, use File.delete?
        activity.revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
}