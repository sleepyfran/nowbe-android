package us.nowbe.nowbe.utils

import android.content.Context
import android.widget.Toast
import us.nowbe.nowbe.R

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ErrorUtils {
    companion object {
        /**
         * Shows a toast with the no-connection error
         */
        fun showNoConnectionToast(context: Context) {
            Toast.makeText(context, context.getString(R.string.general_no_internet), Toast.LENGTH_LONG).show()
        }
    }
}