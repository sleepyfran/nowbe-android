package us.nowbe.nowbe.utils

import android.content.Context
import android.os.Environment
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class InternalStorageUtils {
    companion object {
        /**
         * Name of the file to save the cache
         */
        const val HTTP_CACHE_FILE = "httpCache.nb"

        /**
         * Name of the temporary image file
         */
        val TEMP_IMG_NAME = "tempImg"

        /**
         * Suffix of the temporary image file
         */
        val TEMP_IMG_SUFFIX = ".jpeg"

        /**
         * Returns a temporary file to store images to upload
         */
        fun getTemporaryImageFile(context: Context): File {
            // Get the dir in which we're going to store the image
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            // Return the temporary file
            return File.createTempFile(TEMP_IMG_NAME, TEMP_IMG_SUFFIX, storageDir)
        }

        /**
         * Deletes a temporary file
         */
        fun deleteTemporaryImagefile(context: Context, filePath: String) {
            // Get the file
            val file = File(filePath)

            // Delete it
            file.delete()
        }
    }
}