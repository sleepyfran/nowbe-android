package us.nowbe.nowbe.utils

import android.app.Activity
import android.net.Uri
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import us.nowbe.nowbe.R
import java.io.File

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CropUtils {
    companion object {
        /**
         * Starts an activity to crop an image with an square guideline from an uri
         */
        fun startSquareCroppingActivityFromUri(currentActivity: Activity, uri: Uri) {
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setActivityMenuIconColor(R.color.primary)
                    .setFixAspectRatio(true)
                    .start(currentActivity)
        }

        /**
         * Starts an activity to crop an image with an square guideline from a file
         */
        fun startSquareCroppingActivityFromFile(currentActivity: Activity, file: File) {
            startSquareCroppingActivityFromUri(currentActivity, Uri.fromFile(file))
        }

        /**
         * Starts an activity to crop an image with an square guideline from a file path
         */
        fun startSquareCroppingActivityFromFilePath(currentActivity: Activity, filePath: String) {
            startSquareCroppingActivityFromFile(currentActivity, File(filePath))
        }
    }
}