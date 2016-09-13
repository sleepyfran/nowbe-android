package us.nowbe.nowbe.ui.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.ui.activities.WizardActivity

class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_welcome, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show the custom typeface
        val nowbeTypeface = Typeface.createFromAsset(activity.assets, "fonts/opensans.ttf")
        tvAppName.typeface = nowbeTypeface
        tvAppSlogan.typeface = nowbeTypeface
        tvAppDescription.typeface = nowbeTypeface

        btnLogin.setOnClickListener { (activity as WizardActivity).switchTo(WizardActivity.FragmentsEnum.LOGIN) }

        btnSignUp.setOnClickListener { (activity as WizardActivity).switchTo(WizardActivity.FragmentsEnum.SIGNUP) }
    }
}
