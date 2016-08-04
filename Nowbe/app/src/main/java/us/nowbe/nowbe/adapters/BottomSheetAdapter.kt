package us.nowbe.nowbe.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.holders.BottomSheetViewHolder
import us.nowbe.nowbe.model.BottomSheetItem
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class BottomSheetAdapter(val onItemClick: Interfaces.OnBottomSheetItemClick?) : RecyclerView.Adapter<BottomSheetViewHolder>() {
    /**
     * Items to display on the recycler view
     */
    var items: MutableList<BottomSheetItem> = mutableListOf()

    fun updateItems(newItems: MutableList<BottomSheetItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BottomSheetViewHolder {
        // Inflate the BottomSheet view and return a holder from it
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.bottom_sheet_item, parent, false)
        return BottomSheetViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: BottomSheetViewHolder?, position: Int) {
        holder?.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}