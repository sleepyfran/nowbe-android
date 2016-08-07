package us.nowbe.nowbe.ui.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.amulyakhare.textdrawable.TextDrawable
import kotlinx.android.synthetic.main.comments_slots_comment_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentsSlotsCommentView : RelativeLayout {
    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.comments_slots_comment_view, this, true)
    }

    fun update(comment: Slot) {
        // Make a comment number out of the index of the slot
        val commentIndex = TextDrawable.builder()
                .buildRound(context.getString(R.string.profile_comment_number, (comment.index + 1)),
                        context.resources.getColor(R.color.accent))
        ivCommentNumber.setImageDrawable(commentIndex)

        // Set the comment text
        if (comment.data == ApiUtils.EMPTY) {
            tvCommentText.text = context.getString(R.string.profile_edit_unset)
            tvCommentText.typeface = Typeface.DEFAULT_BOLD
        } else {
            tvCommentText.text = comment.data
            tvCommentText.typeface = Typeface.DEFAULT
        }
    }
}