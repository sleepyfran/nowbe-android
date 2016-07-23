package us.nowbe.nowbe.adapters

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import us.nowbe.nowbe.adapters.holders.FeedHolder
import us.nowbe.nowbe.model.FeedContent
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.views.FeedView

class FeedAdapter : RecyclerView.Adapter<FeedHolder>() {
    /**
     * List of content to display
     */
    var feed: MutableList<FeedContent> = arrayListOf()

    /**
     * Interface to be called when the user presses an item
     */
    lateinit var onClick: Interfaces.OnFeedItemClick

    /**
     * Updates the feed with new content and notifies about the change
     */
    fun updateFeed(content: MutableList<FeedContent>) {
        feed.clear()
        feed.addAll(content)
        notifyDataSetChanged()
    }

    /**
     * Returns the content of the specified position of the feed
     */
    fun getFeedItem(position: Int): FeedContent {
        return feed[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val holder = FeedHolder(FeedView(parent.context))

        // Set the holder on click listener
        holder.itemView.setOnClickListener({ onClick.onFeedItemClick(holder.adapterPosition) })

        return holder
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.bindContent(feed[position])
    }

    override fun getItemCount(): Int {
        return feed.size
    }
}
