package us.nowbe.nowbe.ui.dialogs

import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.BottomSheetItem

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentEditionDialog : BottomSheetDialog() {

    companion object {
        /**
         * Creates a new instance of the dialog with on remove implementation
         */
        fun newInstance(onEdit: () -> Unit, onRemove: () -> Unit): BottomSheetDialog {
            val dialog = CommentEditionDialog()
            dialog.onEdit = onEdit
            dialog.onRemove = onRemove
            return dialog
        }
    }

    /**
     * Method to call when the edit button is pressed
     */
    lateinit var onEdit: () -> Unit

    /**
     * Method to call when the remove button is pressed
     */
    lateinit var onRemove: () -> Unit

    override fun onClick(item: BottomSheetItem) {
        super.onClick(item)

        if (item.drawable == R.drawable.ic_edit_black) {
            onEdit()
        } else if (item.drawable == R.drawable.ic_clear_black_24dp) {
            onRemove()
        }
    }

    override fun getItems(): MutableList<BottomSheetItem> {
        return mutableListOf(
                BottomSheetItem(R.drawable.ic_edit_black, R.string.select_picture_source_edit),
                BottomSheetItem(R.drawable.ic_clear_black_24dp, R.string.select_picture_source_remove))
    }
}