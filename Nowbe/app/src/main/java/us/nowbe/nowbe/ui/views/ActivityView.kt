package us.nowbe.nowbe.ui.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.ActivityContent
import us.nowbe.nowbe.utils.CircleTransform

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ActivityView(context: Context) : RelativeLayout(context) {

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.activity_view, this, true)
    }

    fun update(activity: ActivityContent) {
        // Load the user picture
        Glide.with(context)
                .load(activity.pictureDirWhoMadeActivity)
                .crossFade()
                .error(R.drawable.nowbe_logo)
                .transform(CircleTransform(context))
                .into(ivUserPicture)

        tvUpdateText.text = activity.activity
        tvDate.text = activity.date
    }
}