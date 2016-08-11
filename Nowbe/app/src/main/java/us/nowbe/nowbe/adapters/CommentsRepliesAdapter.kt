package us.nowbe.nowbe.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.holders.CommentsRepliesHolder
import us.nowbe.nowbe.model.CommentReply
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentsRepliesAdapter : RecyclerView.Adapter<CommentsRepliesHolder>() {
    /**
     * Feedback of the comment
     */
    val feedbackList: MutableList<CommentReply> = mutableListOf()

    /**
     * Interface to be called when the user presses an item
     */
    var onClick: Interfaces.OnCommentReplyClick? = null

    /**
     * Updates the adapter with the new content and notifies about it
     */
    fun updateFeedback(content: MutableList<CommentReply>) {
        feedbackList.clear()
        feedbackList.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CommentsRepliesHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item_comment_replies, parent, false)
        val holder = CommentsRepliesHolder(view)

        // Set the item view click action
        holder.itemView.setOnClickListener { onClick?.onClick(feedbackList[holder.adapterPosition]) }

        return holder
    }

    override fun onBindViewHolder(holder: CommentsRepliesHolder?, position: Int) {
        holder?.bindView(feedbackList[position])
    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }
}