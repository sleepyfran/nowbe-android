package us.nowbe.nowbe.adapters.holders

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.feed_view.view.*
import us.nowbe.nowbe.model.FeedContent
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.views.FeedView

class FeedHolder(val feedView: FeedView) : RecyclerView.ViewHolder(feedView) {
    /**
     * Binds a FeedContent into the view
     */
    fun bindContent(content: FeedContent) {
        // Bind the content
        feedView.update(content)
    }
}