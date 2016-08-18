package us.nowbe.nowbe.adapters.holders

import android.support.v7.widget.RecyclerView
import us.nowbe.nowbe.model.ActivityContent
import us.nowbe.nowbe.ui.views.ActivityView

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ActivityHolder(val activityView: ActivityView) : RecyclerView.ViewHolder(activityView) {
    /**
     * Binds an ActivityContent into the view
     */
    fun bindContent(content: ActivityContent) {
        // Bind the content
        activityView.update(content)
    }
}