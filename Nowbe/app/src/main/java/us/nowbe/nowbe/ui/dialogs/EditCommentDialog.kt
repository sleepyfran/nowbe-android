package us.nowbe.nowbe.ui.dialogs

import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_edit_general_text.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.async.UpdateUserCommentObservable
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class EditCommentDialog : EditWithTextFieldDialog() {
    /**
     * Index of the comment
     */
    var commentIndex: Int = 0

    companion object {
        /**
         * Creates a new instance of the dialog with the onDismiss implementation and the default text
         */
        fun newInstance(onDismiss: DialogInterface.OnDismissListener, commentIndex: Int): EditWithTextFieldDialog {
            val dialog = EditCommentDialog()
            dialog.onDismiss = onDismiss
            dialog.commentIndex = commentIndex
            return dialog
        }
    }

    override fun getTitle(): String {
        return getString(R.string.profile_edit_comment_number_edit, commentIndex)
    }

    override fun isDataValid(view: View): Boolean {
        return true
    }

    override fun getPositiveAction(view: View): () -> Unit {
        return {
            // Get the token from the user
            val token = SharedPreferencesUtils.getToken(context)!!

            // Get the about text
            val newComment = view.tvEditDialogText.text.toString()

            UpdateUserCommentObservable.create(token, commentIndex, newComment).subscribe(
                    // On Next
                    {
                        result ->

                        // Show a toast confirming the change
                        Toast.makeText(activity,
                                getString(R.string.profile_edit_comments_slots_updated, commentIndex),
                                Toast.LENGTH_SHORT).show()
                    },
                    // On Error
                    {
                        error ->

                        if (error is RequestNotSuccessfulException) {
                            ErrorUtils.showGeneralWhoopsDialog(activity)
                        } else {
                            ErrorUtils.showNoConnectionToast(activity)
                        }
                    }
            )
        }
    }
}