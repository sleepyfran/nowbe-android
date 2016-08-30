package us.nowbe.nowbe.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class IntentUtils {
    companion object {
        /**
         * Keys for passing images
         */
        val IMG_DATA = "img"

        /**
         * Keys for the token
         */
        val TOKEN = "token"

        /**
         * Keys for passing cools
         */
        val COOLS = "cools"

        /**
         * Keys for the type of search
         */
        val SEARCH_RESULT = "search"

        /**
         * Keys for animation enabling/disabling
         */
        val ANIMATIONS = "anim"

        /**
         * Keys for the fab position
         */
        val FAB_X_POSITION = "fabX"
        val FAB_Y_POSITION = "fabY"

        /**
         * Keys for image picking
         */
        val REQUEST_CAMERA_IMAGE = 0
        val REQUEST_GALLERY_IMAGE = 1

        /**
         * General builder for an intent with a photo
         */
        fun buildPhotoIntent(context: Context, action: String): Pair<Intent, String> {
            val fileIntent = Intent(action)

            // Create the temporary file to store the photo
            val tempFile = InternalStorageUtils.getTemporaryImageFile(context)

            // Get the photo URI and attach it to the Intent
            val photoUri = Uri.fromFile(tempFile)
            fileIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            // Return a pair with the intent and the file path
            return Pair(fileIntent, tempFile.path)
        }

        /**
         * Builds an intent that picks a photo from the camera
         */
        fun buildCameraIntent(context: Context): Pair<Intent, String> {
            return buildPhotoIntent(context, MediaStore.ACTION_IMAGE_CAPTURE)
        }

        /**
         * builds an intent that picks a photo from the gallery
         */
        fun buildPickPhotoIntent(): Intent {
            val photoIntent = Intent(Intent.ACTION_GET_CONTENT)
            photoIntent.type = "image/*"

            return Intent.createChooser(photoIntent, "Select an image")
        }
    }
}