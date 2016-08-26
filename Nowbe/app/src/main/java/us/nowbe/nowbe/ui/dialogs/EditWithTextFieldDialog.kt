package us.nowbe.nowbe.ui.dialogs

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.dialog_edit_general_text.view.*
import us.nowbe.nowbe.R

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

abstract class EditWithTextFieldDialog : EditDialog() {

    /**
     * Default text to be shown in the text field
     */
    var defaultText: String? = null

    override fun getDialogView(): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_general_text, null, false)

        // Set the default text of the text field
        view.aedCoupleName.setText(defaultText)

        // Show the description only if the subclass wants it
        if (hasDescription()) {
            view.tvDescription.visibility = View.VISIBLE
            view.tvDescription.text = getDescription()
        }

        // Set the cursor at the end of the text field
        view.aedCoupleName.setSelection(view.aedCoupleName.text.length)

        return view
    }

    override fun isDataValid(view: View): Boolean {
        // Get the text that the user wrote
        val data = view.aedCoupleName.text

        return !TextUtils.isEmpty(data)
    }

    /**
     * This should be overridden by the subclass if it wants a description
     */
    open fun hasDescription(): Boolean {
        return false
    }

    /**
     * This should be overridden by the subclass if it wants a description
     */
    open fun getDescription(): String? {
        return null
    }
}