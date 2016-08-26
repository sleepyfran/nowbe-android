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

class SetCoupleDialog {
    companion object {
        /**
         * Creates a dialog asking the user and removes an user if yes is answered
         */
        fun createDialog(context: Context, userFullName: String, onPositive: () -> Unit): AlertDialog {
            return AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.profile_edit_couple))
                    .setMessage(context.getString(R.string.profile_edit_couple_confirmation, userFullName))
                    .setPositiveButton(context.getString(R.string.profile_edit_couple_confirmation_yes),
                            { dialogInterface, i -> onPositive() })
                    .setNegativeButton(context.getString(R.string.profile_edit_couple_confirmation_no), null)
                    .create()
        }
    }
}