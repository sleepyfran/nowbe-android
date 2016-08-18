package us.nowbe.nowbe.adapters.holders

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import us.nowbe.nowbe.model.FeedContent
import us.nowbe.nowbe.ui.views.FeedView

class FeedHolder(val feedView: FeedView) : RecyclerView.ViewHolder(feedView) {
    /**
     * Binds a FeedContent into the view
     */
    fun bindContent(content: FeedContent) {
        // Bind the content
        feedView.update(content)
    }
}