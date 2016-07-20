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
import us.nowbe.nowbe.views.FeedView

class FeedAdapter : RecyclerView.Adapter<FeedHolder>() {
    /**
     * List of content to display
     */
    var feed: MutableList<FeedContent> = arrayListOf()

    /**
     * Updates the feed with new content and notifies about the change
     */
    fun updateFeed(content: MutableList<FeedContent>) {
        feed.clear()
        feed.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        return FeedHolder(FeedView(parent.context))
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.bindContent(feed[position])
    }

    override fun getItemCount(): Int {
        return feed.size
    }
}
