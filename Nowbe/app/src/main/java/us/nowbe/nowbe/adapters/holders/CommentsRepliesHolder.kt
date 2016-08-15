package us.nowbe.nowbe.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_comment_replies.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.CommentReply
import us.nowbe.nowbe.utils.CircleTransform

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentsRepliesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(feedback: CommentReply) {
        // Bind the content of the feedback into the view
        Glide.with(itemView.context)
                .load(feedback.profilePic)
                .crossFade()
                .transform(CircleTransform(itemView.context))
                .error(R.drawable.nowbe_logo)
                .into(itemView.ivUserPicture)

        itemView.tvUserFullName.text = feedback.fullName
        itemView.tvUsername.text = itemView.context.getString(R.string.profile_username, feedback.username)
        itemView.tvCommentFeedback.text = feedback.text
        itemView.tvCommentDate.text = feedback.timestamp
    }
}