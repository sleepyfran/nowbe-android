package us.nowbe.nowbe.ui.dialogs

import android.content.Context
import android.support.v7.app.AlertDialog
import us.nowbe.nowbe.R

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ChangeAvailabilityDialog {

    companion object {
        /**
         * Creates a dialog asking the user and changes its availability
         */
        fun createDialog(context: Context, onPositive: () -> Unit, onNegative: () -> Unit): AlertDialog {
            return AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.available_title))
                    .setMessage(context.getString(R.string.available_message))
                    .setPositiveButton(context.getString(R.string.available_yeah),
                            { dialogInterface, i -> onPositive() })
                    .setNegativeButton(context.getString(R.string.available_no),
                            { dialogInterface, i -> onNegative() })
                    .create()
        }
    }
}