package us.nowbe.nowbe.adapters

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import us.nowbe.nowbe.adapters.holders.CommentHolder
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.ui.views.CommentsSlotsCommentView
import us.nowbe.nowbe.utils.Interfaces

class CommentsAdapter : RecyclerView.Adapter<CommentHolder>() {
    /**
     * List of comments to display
     */
    var comments: MutableList<Slot> = arrayListOf()

    /**
     * Action to perform when clicking a slot
     */
    var onClick: Interfaces.OnCommentSlotClick? = null

    /**
     * Deletes all the current comments
     */
    fun clear() {
        comments = arrayListOf()
    }

    /**
     * Adds a comment to the array
     */
    fun addComment(slot: Slot) {
        comments.add(slot)
        notifyItemChanged(comments.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val holder = CommentHolder(CommentsSlotsCommentView(parent.context))

        // Set the onClick listener for the view
        holder.itemView.setOnClickListener({
            onClick?.onCommentSlotClick(comments[holder.adapterPosition].data, comments[holder.adapterPosition].index)
        })
        return holder
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        if (comments.size > 0) {
            holder.bindView(comments[position])
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}
