package us.nowbe.nowbe.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import us.nowbe.nowbe.adapters.holders.ActivityHolder
import us.nowbe.nowbe.model.ActivityContent
import us.nowbe.nowbe.ui.views.ActivityView
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ActivityAdapter : RecyclerView.Adapter<ActivityHolder>() {
    /**
     * List of content to display
     */
    var activity: MutableList<ActivityContent> = arrayListOf()

    /**
     * Interface to be called when the user presses an item
     */
    var onLongClick: Interfaces.OnActivityDeleteClick? = null

    /**
     * Updates the activity with new content and notifies about the change
     */
    fun updateActivity(content: MutableList<ActivityContent>) {
        activity.clear()
        activity.addAll(content)
        notifyDataSetChanged()
    }

    /**
     * Removes an activity from the list and notifies the adapter about the changes
     */
    fun removeActivity(index: Int) {
        activity.removeAt(index)
        notifyItemRemoved(index)
    }

    /**
     * Removes all the activities from the list and nofities the adapter
     */
    fun removeAllActivity() {
        val previousSize = activity.size

        activity.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityHolder {
        val holder = ActivityHolder(ActivityView(parent.context))

        // Set the holder on click listener
        holder.itemView.setOnLongClickListener {
            // Call the onClick interface (if any)
            onLongClick?.onActivityDeleteClick(activity[holder.adapterPosition].id, holder.adapterPosition)

            // We'll handle this!
            true
        }

        return holder
    }

    override fun onBindViewHolder(holder: ActivityHolder, position: Int) {
        holder.bindContent(activity[position])
    }

    override fun getItemCount(): Int {
        return activity.size
    }
}