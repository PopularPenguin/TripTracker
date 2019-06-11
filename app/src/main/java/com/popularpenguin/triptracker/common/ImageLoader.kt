package com.popularpenguin.triptracker.common

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.Metadata
import com.drew.metadata.MetadataException
import com.drew.metadata.exif.ExifIFD0Directory
import com.squareup.picasso.Picasso
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageLoader {

    fun load(photoUri: Uri, view: ImageView, fit: Boolean = false) {
        val request = Picasso.get()
            .load(photoUri) // TODO: Add placeholder and error images

        if (fit) {
            request.fit()
        }

        request.into(view)
    }

    fun storePhoto(contentResolver: ContentResolver, photoUri: Uri, photoFile: File) {
        val rotation = getRotation(contentResolver, photoUri)
        val matrix = Matrix().apply { postRotate(rotation) }
        val bitmap = BitmapFactory.decodeFile(photoFile.path)
        val finalBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        storeAsJpg(contentResolver, photoFile, finalBitmap)
    }

    private fun getRotation(contentResolver: ContentResolver, photoUri: Uri): Float {
        var orientation = 0
        try {
            val inputStream = contentResolver.openInputStream(photoUri)
            var metadata: Metadata? = null

            if (inputStream != null) {
                try {
                    metadata = ImageMetadataReader.readMetadata(BufferedInputStream(inputStream))
                } catch (e: ImageProcessingException) {
                    e.printStackTrace()
                }

                if (metadata != null) {
                    val exifIFD0Directory =
                        metadata.getFirstDirectoryOfType(ExifIFD0Directory::class.java) as ExifIFD0Directory

                    if (exifIFD0Directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                        try {
                            orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION)
                        } catch (e: MetadataException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> 90.0f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180.0f
            ExifInterface.ORIENTATION_ROTATE_270 -> 270.0f
            else -> 0.0f
        }
    }

    private fun storeAsJpg(contentResolver: ContentResolver, photoFile: File, bitmap: Bitmap) {
        var outputStream: FileOutputStream? = null

        try {
            outputStream = FileOutputStream(photoFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            /*
            MediaStore.Images.Media.insertImage(
                contentResolver,
                bitmap,
                photoFile.name,
                photoFile.name
            ) */
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStream?.close()
        }
    }
}
