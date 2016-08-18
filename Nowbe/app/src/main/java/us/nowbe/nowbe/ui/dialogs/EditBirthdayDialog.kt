package us.nowbe.nowbe.ui.dialogs

import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import us.nowbe.nowbe.R
import us.nowbe.nowbe.net.async.UpdateUserBirthdayObservable
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.utils.SharedPreferencesUtils
import java.util.*

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class EditBirthdayDialog : EditDialog() {

    companion object {
        /**
         * Creates a new instance of the dialog with the onDismiss implementation
         */
        fun newInstance(onDismiss: Interfaces.OnDialogDismiss): EditDialog {
            val dialog = EditBirthdayDialog()
            dialog.onDismiss = onDismiss
            return dialog
        }
    }

    override fun getDialogView(): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_general_date, null, false)

        // Cast the view
        view as DatePicker

        // Initialize the date picker
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)
        view.init(year, month, day, null)
        view.maxDate = today.timeInMillis

        return view
    }

    override fun getTitle(): String? {
        // We don't want a title here
        return null
    }

    override fun getPositiveAction(view: View, onDismiss: Interfaces.OnDialogDismiss): () -> Unit {
        return {
            // Get the age from the selected date
            val datePicker = view as DatePicker
            val year = datePicker.year
            val month = datePicker.month + 1
            val day = datePicker.dayOfMonth
            val date = getString(R.string.format_date, year, month, day)
            val token = SharedPreferencesUtils.getToken(context)!!

            // Update the age of the user
            previousSubscription = UpdateUserBirthdayObservable.create(token, date).subscribe(
                    // On Next
                    {
                        result ->

                        // Show a toast confirming the change
                        Toast.makeText(activity, getString(R.string.profile_edit_birthday_updated), Toast.LENGTH_SHORT).show()

                        // Call the onDismiss to indicate we've finished already
                        onDismiss.onDismiss()

                        // Dismiss the dialog
                        dismiss()
                    },
                    // On Error
                    {
                        // Show a general error toast
                        ErrorUtils.showGeneralWhoopsDialog(activity)
                    }
            )
        }
    }

    override fun isDataValid(view: View): Boolean {
        // This should always be true, keeping this allows us to enter an age restriction with ease
        return true
    }
}