package us.nowbe.nowbe.adapters.holders

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.View
import com.amulyakhare.textdrawable.TextDrawable
import kotlinx.android.synthetic.main.list_item_comment.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.Slot

class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Binds a comment into the view
     */
    fun bindView(comment: Slot) {
        // Make a comment number out of the index of the slot
        val commentIndex = TextDrawable.builder()
                .buildRound(itemView.context.getString(R.string.profile_comment_number, (comment.index + 1)),
                itemView.context.resources.getColor(R.color.accent))
        itemView.ivCommentNumber.setImageDrawable(commentIndex)

        // Set the comment text
        itemView.tvCommentText.text = comment.data
    }
}