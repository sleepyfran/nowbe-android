package us.nowbe.nowbe.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_tutorial.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.net.async.SetTutorialSeenObservable
import us.nowbe.nowbe.ui.activities.LandingActivity
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class TutorialFragment : Fragment() {

    companion object {
        /**
         * Creates a new instance of the fragment with the specified URL
         */
        fun newInstance(link: String, isFinal: Boolean = false): TutorialFragment {
            val tutorialFragment = TutorialFragment()
            tutorialFragment.tutorialLink = link
            tutorialFragment.isFinal = isFinal
            return tutorialFragment
        }
    }

    /**
     * Indicates the link that this web view should show
     */
    lateinit var tutorialLink: String

    /**
     * Indicates whether this page is the final one or not
     */
    var isFinal: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load the link
        wvTutorial.loadUrl(tutorialLink)

        // Show the understood button if this page is the last one
        if (isFinal) {
            llUnderstood.visibility = View.VISIBLE
        } else {
            llUnderstood.visibility = View.GONE
        }

        btnUnderstood.setOnClickListener {
            // Get the token of the user
            val userToken = SharedPreferencesUtils.getToken(context)!!

            // Set the tutorial seen in the server
            SetTutorialSeenObservable.create(userToken).subscribe()

            val landingIntent = Intent(activity, LandingActivity::class.java)
            landingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(landingIntent)
        }
    }
}