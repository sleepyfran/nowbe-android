package us.nowbe.nowbe.adapters

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.holders.PicturesSlotsHolder
import us.nowbe.nowbe.utils.OnClick
import us.nowbe.nowbe.views.PicturesSlotsPictureView

class PicturesSlotsAdapter(val onClick: OnClick.OnPictureSlotClick) : RecyclerView.Adapter<PicturesSlotsHolder>() {
    /**
     * Array of pictures of the user
     */
    private var pictures = Array(itemCount, {R.drawable.placeholder_pic})

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesSlotsHolder {
        return PicturesSlotsHolder(PicturesSlotsPictureView(parent.context))
    }

    override fun onBindViewHolder(holder: PicturesSlotsHolder, position: Int) {
        holder.bindView(pictures[position], 560, onClick, position)
    }

    override fun getItemCount(): Int {
        return 5
    }
}
