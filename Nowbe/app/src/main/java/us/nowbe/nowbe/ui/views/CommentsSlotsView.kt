package us.nowbe.nowbe.ui.views

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.comments_slots_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.CommentsAdapter
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentsSlotsView : RelativeLayout {
    /**
     * Adapter of the view
     */
    private val adapter: CommentsAdapter

    /**
     * Defines whether the view should display null content or not
     */
    var allowNullValues: Boolean = false

    /**
     * Action to perform when clicking a slot
     */
    var onClick: Interfaces.OnCommentSlotClick? = null
        set(value) {
            adapter.onClick = value
        }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // Get the attributes
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.CommentsSlotsView, 0, 0)

        // Try to set the custom attributes
        try {
            allowNullValues = attributes.getBoolean(R.styleable.CommentsSlotsView_allowNullValues, false)
        } finally {
            // Recycle the attributes
            attributes.recycle()

            // Inflate the view
            LayoutInflater.from(context).inflate(R.layout.comments_slots_view, this, true)

            // Setup the recycler view
            adapter = CommentsAdapter()
            rvCommentsSlots.adapter = adapter
            rvCommentsSlots.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    /**
     * Updates the current pictures with the new ones
     */
    fun updateSlots(user: User) {
        // Clear the current comments slots
        adapter.clear()

        // Add the pictures slots
        for (comment in user.commentsSlots) {
            if (!allowNullValues && comment?.data == ApiUtils.EMPTY) {
                continue
            }

            adapter.addComment(comment!!)
        }
    }
}