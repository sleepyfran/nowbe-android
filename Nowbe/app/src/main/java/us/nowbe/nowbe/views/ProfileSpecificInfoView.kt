package us.nowbe.nowbe.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.profile_specific_info_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.User

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ProfileSpecificInfoView : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.profile_specific_info_view, this, true)
    }

    fun updateInformation(user: User) {
        // If all the information is null hide the whole layout
        if (user.interests == null && user.education == null && user.coupleToken == null) {
            llProfileLikesAndEducationRoot.visibility = View.GONE
            return
        }

        // If not, hide the null information individually or populate it
        if (user.interests == null) {
            llInterests.visibility = GONE
        } else {
            tvInterests.text = user.interests
        }

        if (user.education == null) {
            llEducation.visibility = GONE
        } else {
            tvEducation.text = user.education
        }

        if (user.coupleName == null) {
            llCouple.visibility = GONE
        } else {
            tvCouple.text == user.coupleName

            // TODO: Send the user to the couple's profile when onClick
        }
    }
}