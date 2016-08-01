package us.nowbe.nowbe.adapters

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.holders.CommentHolder
import us.nowbe.nowbe.model.Slot

class CommentsAdapter : RecyclerView.Adapter<CommentHolder>() {
    /**
     * List of comments to display
     */
    var comments: MutableList<Slot> = arrayListOf()

    fun updateComments(content: MutableList<Slot>) {
        comments.clear()
        comments.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        // Inflate the view and return a comment holder from it
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_comment, parent, false)
        return CommentHolder(view)
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
