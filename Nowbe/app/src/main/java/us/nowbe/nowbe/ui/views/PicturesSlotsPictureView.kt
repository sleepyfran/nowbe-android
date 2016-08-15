package us.nowbe.nowbe.ui.views

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pictures_slots_picture_view.view.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.CircleTransform

class PicturesSlotsPictureView : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.pictures_slots_picture_view, this, true)
    }

    /**
     * Updates the image of the slot
     */
    fun updateImage(url: String) {
        Glide.with(context)
                .load(ApiUtils.getThumbSlotPicDir(url, true))
                .crossFade()
                .transform(CircleTransform(context))
                .error(R.drawable.nowbe_logo)
                .into(ivPictureSlot)
    }

    /**
     * Displays a placeholder in the slot
     */
    fun displayPlaceholder() {
        Glide.with(context)
                .load(R.drawable.nowbe_logo)
                .crossFade()
                .into(ivPictureSlot)
    }

    /**
     * Updates the cool counter of the slot
     */
    fun updateCounter(count: String) {
        val counter = TextDrawable.builder().buildRoundRect(count,
                context.resources.getColor(R.color.accent), 10)

        ivCoolsCounter.setImageDrawable(counter)
    }

    /**
     * Hides the cool counter of the slot
     */
    fun hideCounter() {
        ivCoolsCounter.visibility = View.GONE
    }
}