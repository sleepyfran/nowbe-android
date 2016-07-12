package us.nowbe.nowbe.views

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
import android.widget.Toast
import kotlinx.android.synthetic.main.pictures_slot_view.view.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.PicturesSlotsAdapter
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.OnClick

class PicturesSlotsView : RelativeLayout {
    /**
     * Adapter of the view
     */
    private val adapter: PicturesSlotsAdapter

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.pictures_slot_view, this, true)

        // Setup the recycler view
        adapter = PicturesSlotsAdapter(object : OnClick.OnPictureSlotClick {
            override fun onPictureSlotClick(itemSelected: Int) {
                // TODO: Show the picture
                Toast.makeText(context, itemSelected.toString(), Toast.LENGTH_LONG).show()
            }
        })
        rvPicturesSlots.adapter = adapter
        rvPicturesSlots.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    /**
     * Updates the current pictures with the new ones
     */
    fun updateSlots(user: User) {
        // Clear the current pictures slots
        adapter.clear()

        // Add non-null picture slots
        for (picture in user.picturesSlots.filterNotNull()) {
            adapter.addPicture(picture)
        }
    }
}