package us.nowbe.nowbe.adapters.holders

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.ui.views.CommentsSlotsCommentView

class CommentHolder(val commentView: CommentsSlotsCommentView) : RecyclerView.ViewHolder(commentView) {
    /**
     * Binds a comment into the view
     */
    fun bindView(comment: Slot) {
        commentView.update(comment)
    }
}