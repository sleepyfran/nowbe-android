package us.nowbe.nowbe.ui.dialogs

import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_edit_general_text.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.net.async.UpdateUserAboutObservable
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class EditAboutDialog : EditWithTextFieldDialog() {

    companion object {
        /**
         * Creates a new instance of the dialog with the onDismiss implementation and the default text
         */
        fun newInstance(onDismiss: Interfaces.OnDialogDismiss, defaultText: String): EditWithTextFieldDialog {
            val dialog = EditAboutDialog()
            dialog.onDismiss = onDismiss
            dialog.defaultText = defaultText
            return dialog
        }
    }

    override fun getTitle(): String {
        return getString(R.string.profile_edit_about_user)
    }

    override fun getPositiveAction(view: View, onDismiss: Interfaces.OnDialogDismiss): () -> Unit {
        return {
            // Get the token from the user
            val token = SharedPreferencesUtils.getToken(context)!!

            // Get the about text
            val newAbout = view.tvEditDialogText.text.toString()

            UpdateUserAboutObservable.create(token, newAbout).subscribe(
                    // On Next
                    {
                        result ->

                        // Show a toast confirming the change
                        Toast.makeText(activity, getString(R.string.profile_edit_about_user_updated), Toast.LENGTH_SHORT).show()

                        // Call the onDismiss to indicate we've finished already
                        onDismiss.onDismiss()

                        // Dismiss the dialog
                        dismiss()
                    },
                    // On Error
                    {
                        // Show a general error, we don't know why we got the 0!
                        ErrorUtils.showGeneralWhoopsDialog(activity)
                    }
            )
        }
    }
}