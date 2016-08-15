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

class DeleteReplyDialog {
    companion object {
        /**
         * Creates the dialog asking the user and removes the reply if yes is answered
         */
        fun createDialog(context: Context, onPositive: () -> Unit): AlertDialog {
            return AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.comments_details_delete_reply_title))
                    .setMessage(context.getString(R.string.comments_details_delete_reply_message))
                    .setPositiveButton(context.getString(R.string.comments_details_delete_reply_yes),
                            { dialogInterface, i -> onPositive() })
                    .setNegativeButton(context.getString(R.string.comments_details_delete_reply_no), null)
                    .create()
        }
    }
}