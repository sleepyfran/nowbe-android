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

class DeleteActivityDialog {
    companion object {
        /**
         * Creates a dialog asking the user and removes an user if yes is answered
         */
        fun createDialog(context: Context, onPositive: () -> Unit): AlertDialog {
            return AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.activity_delete_activity_title))
                    .setMessage(context.getString(R.string.activity_delete_activity_message))
                    .setPositiveButton(context.getString(R.string.activity_delete_activity_yes),
                            { dialogInterface, i -> onPositive() })
                    .setNegativeButton(context.getString(R.string.activity_delete_activity_no), null)
                    .create()
        }
    }
}