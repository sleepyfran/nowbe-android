package us.nowbe.nowbe.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.View
import us.nowbe.nowbe.R

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

abstract class EditDialog() : DialogFragment() {
    /**
     * Callback when the dialog is dismissed
     */
    lateinit var onDismiss: DialogInterface.OnDismissListener

    /**
     * Indicates whether the user has updated anything or not
     */
    var hasUpdated = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // View of the dialog (implemented by the subclass)
        val dialogView = getDialogView()

        val dialog = AlertDialog.Builder(activity)
                .setView(dialogView)
                .setTitle(getTitle())
                .setPositiveButton(getString(R.string.profile_edit_update), null)
                .setNegativeButton(getString(R.string.profile_edit_cancel), null)
                .create()

        // Override the positive's button onClickListener so the dialog doesn't dismiss if the data is invalid
        dialog.setOnShowListener {
            val btnOk = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

            btnOk.setOnClickListener {

                // Dismiss the dialog only if the data provided is valid
                if (isDataValid(dialogView)) {
                    // The data is valid so we want an update
                    hasUpdated = true

                    // Perform the action of the positive button and dismiss the dialog
                    dismiss()
                    getPositiveAction(dialogView)()
                }
            }
        }

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface?) {
        // Callback only if the user has updated anything and it's valid
        if (hasUpdated) onDismiss.onDismiss(dialog)

        super.onDismiss(dialog)
    }

    /**
     * Method to be implemented by the subclass and that's going to be called when the dialog needs the view of the dialog
     */
    abstract fun getDialogView(): View

    /**
     * Method to be implemented by the subclass and that's going to be called when the dialog needs a title
     */
    abstract fun getTitle(): String?

    /**
     * Method to be implemented by the subclass and that's going to be called when the dialog calls the positive action
     */
    abstract fun getPositiveAction(view: View): () -> Unit

    /**
     * Method to call when we need to check whether the data (input of the user) is or not valid
     */
    abstract fun isDataValid(view: View): Boolean
}