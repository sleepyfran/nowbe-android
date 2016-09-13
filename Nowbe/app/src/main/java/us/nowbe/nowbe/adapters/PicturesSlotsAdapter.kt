package us.nowbe.nowbe.adapters

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import us.nowbe.nowbe.adapters.holders.PicturesSlotsHolder
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.ui.views.PicturesSlotsPictureView
import us.nowbe.nowbe.utils.Interfaces

class PicturesSlotsAdapter : RecyclerView.Adapter<PicturesSlotsHolder>() {
    /**
     * Array of pictures of the user
     */
    var pictures: MutableList<Slot> = arrayListOf()

    /**
     * Action to perform when clicking a slot
     */
    var onClick: Interfaces.OnPictureSlotClick? = null

    /**
     * Deletes all the current pictures
     */
    fun clear() {
        pictures = arrayListOf()
        notifyDataSetChanged()
    }

    /**
     * Returns a slot from an index
     */
    fun getSlot(index: Int): Slot {
        return pictures[index]
    }

    /**
     * Adds a picture to the array
     */
    fun addPicture(slot: Slot) {
        pictures.add(slot)
        notifyItemInserted(pictures.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesSlotsHolder {
        val holder = PicturesSlotsHolder(PicturesSlotsPictureView(parent.context))

        // Set the onClick listener for the view
        holder.itemView.setOnClickListener({
            onClick?.onPictureSlotClick(pictures[holder.adapterPosition].index, holder.adapterPosition)
        })
        return holder
    }

    override fun onBindViewHolder(holder: PicturesSlotsHolder, position: Int) {
        if (pictures.size > 0) {
            holder.bindView(pictures[position].data, pictures[position].cools!!)
        }
    }

    override fun getItemCount(): Int {
        return pictures.size
    }
}
