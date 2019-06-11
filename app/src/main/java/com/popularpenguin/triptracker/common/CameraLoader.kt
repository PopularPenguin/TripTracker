package com.popularpenguin.triptracker.common

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.popularpenguin.triptracker.R

class CameraLoader(private val activity: Activity, cameraView: View) {

    val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    var cameraReady = false
    var resolveActivity = false

    private val packageManager = activity.packageManager
    private val permissionValidator = PermissionValidator(activity)

    init {
        val deviceHasCamera = deviceHasCamera()
        val deviceHasPermission = deviceHasPermission()

        cameraReady = deviceHasCamera && deviceHasPermission
        resolveActivity = captureIntent.resolveActivity(packageManager) != null

        if (!deviceHasCamera) {
            TripSnackbar(cameraView, R.string.snackbar_no_camera, Snackbar.LENGTH_SHORT)
                .show()
        } else if (!deviceHasPermission()) {
            TripSnackbar(cameraView, R.string.permissions_storage, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_settings) {
                    activity.startActivity(permissionValidator.settingsIntent)
                }.show()
        }
    }

    fun grantUriPermission(uri: Uri) {
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        val cameraActivities = packageManager.queryIntentActivities(
            captureIntent,
            PackageManager.MATCH_DEFAULT_ONLY
        )

        for (cameraActivity in cameraActivities) {
            activity.grantUriPermission(
                cameraActivity.activityInfo.packageName,
                uri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
        }
    }

    private fun deviceHasCamera(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    private fun deviceHasPermission(): Boolean {
        return permissionValidator.checkStoragePermission()
    }
}