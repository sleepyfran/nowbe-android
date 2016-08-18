package us.nowbe.nowbe.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet_layout.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.BottomSheetAdapter
import us.nowbe.nowbe.model.BottomSheetItem
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

abstract class BottomSheetDialog : BottomSheetDialogFragment(), Interfaces.OnBottomSheetItemClick {
    /**
     * Adapter of the bottom sheet dialog
     */
    val adapter = BottomSheetAdapter(this)

    /**
     * Behavior of the bottom sheet
     */
    lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create a dialog and its view
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.bottom_sheet_layout, null)

        // Setup the recycler view
        view.rvBottomSheet.setHasFixedSize(true)
        view.rvBottomSheet.layoutManager = LinearLayoutManager(context)

        // Get the items specified in the subclass
        adapter.updateItems(getItems())

        // Setup the adapter of the recycler view
        view.rvBottomSheet.adapter = adapter

        // Set the view of the dialog
        dialog.setContentView(view)

        // Setup the behavior of the dialog
        behavior = BottomSheetBehavior.from(view.parent as View)

        return dialog
    }

    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    /**
     * (Should) be overridden by the subclass to handle the passed item
     */
    override fun onClick(item: BottomSheetItem) {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    /**
     * Method to be implemented by the subclass when the bottom sheet need its items
     */
    abstract fun getItems(): MutableList<BottomSheetItem>
}