package us.nowbe.nowbe.dialogs

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

    override fun getDialogView(): View {
        return LayoutInflater.from(activity).inflate(R.layout.dialog_edit_general_text, null, false)
    }

    override fun getTitle(): String {
        return getString(R.string.profile_edit_full_name)
    }

    override fun isDataValid(view: View): Boolean {
        // Get the text that the user wrote
        val data = view.tvEditDialogText.text

        return !TextUtils.isEmpty(data)
    }
}