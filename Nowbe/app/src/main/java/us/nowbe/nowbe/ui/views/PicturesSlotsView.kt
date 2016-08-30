package us.nowbe.nowbe.ui.views

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.pictures_slot_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.PicturesSlotsAdapter
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.Interfaces

class PicturesSlotsView : RelativeLayout {

    /**
     * Adapter of the view
     */
    private val adapter: PicturesSlotsAdapter

    /**
     * Defines whether the view should position itself as horizontal or vertical
     */
    var orientation: Int = LinearLayoutManager.HORIZONTAL
        set(value) {
            if (value == LinearLayoutManager.HORIZONTAL || value == LinearLayoutManager.VERTICAL) {
                field = value
            }
        }

    /**
     * Defines whether the view should display null content or not
     */
    var allowNullValues: Boolean = false

    /**
     * Action to perform when clicking a slot
     */
    var onClick: Interfaces.OnPictureSlotClick? = null
        set(value) {
            adapter.onClick = value
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // Get the attributes
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.PicturesSlotsView, 0, 0)

        // Try to set the custom attributes
        try {
            orientation = attributes.getInt(R.styleable.PicturesSlotsView_orientation, LinearLayoutManager.HORIZONTAL)
            allowNullValues = attributes.getBoolean(R.styleable.PicturesSlotsView_allowNullValues, false)
        } finally {
            // Recycle the attributes
            attributes.recycle()

            // Inflate the view
            LayoutInflater.from(context).inflate(R.layout.pictures_slot_view, this, true)

            // Setup the recycler view
            adapter = PicturesSlotsAdapter()
            rvPicturesSlots.adapter = adapter
            rvPicturesSlots.layoutManager = LinearLayoutManager(context, orientation, false)
            rvPicturesSlots.isNestedScrollingEnabled = false
        }
    }

    /**
     * Returns the slot given an index
     */
    fun getSlot(index: Int): Slot {
        return adapter.getSlot(index)
    }

    /**
     * Updates the current pictures with the new ones
     */
    fun updateSlots(user: User) {
        // Clear the current pictures slots
        adapter.clear()

        // Add the pictures slots
        for (picture in user.picturesSlots) {
            if (!allowNullValues && picture?.data == ApiUtils.NULL) {
                continue
            }

            adapter.addPicture(picture!!)
        }
    }
}