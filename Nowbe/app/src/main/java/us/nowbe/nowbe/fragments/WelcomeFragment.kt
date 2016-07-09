package us.nowbe.nowbe.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.activities.WizardActivity

class WelcomeFragment : Fragment {
    constructor() : super()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_welcome, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener { (activity as WizardActivity).switchTo(WizardActivity.FragmentsEnum.LOGIN) }

        btnSignUp.setOnClickListener { (activity as WizardActivity).switchTo(WizardActivity.FragmentsEnum.SIGNUP) }
    }
}
