package us.nowbe.nowbe.adapters.holders

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet_item.view.*
import us.nowbe.nowbe.model.BottomSheetItem
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class BottomSheetViewHolder(itemView: View, val onItemClick: Interfaces.OnBottomSheetItemClick?) :
        RecyclerView.ViewHolder(itemView) {
    /**
     * Item bound to the view
     */
    lateinit var item: BottomSheetItem

    init {
        // Notify about the click only if it's not null
        itemView.setOnClickListener{
            onItemClick?.onClick(item)
        }
    }

    /**
     * Binds a drawable and a text to the view
     */
    fun bindView(item: BottomSheetItem) {
        // Save the item
        this.item = item

        // Set the image and text
        itemView.ivBottomSheetIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.drawable))
        itemView.ivBottomSheetText.text = itemView.context.getString(item.text)
    }
}