package us.nowbe.nowbe.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_profile.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.CommentsAdapter
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.net.NowbeUserData
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.NetUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class ProfileFragment : Fragment() {
    /**
     * Token to which the profile refers to
     */
    lateinit var token: String

    companion object {
        /**
         * Returns a new instance with the specified token as the one to use
         */
        fun newInstance(token: String): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.token = token
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_profile, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the (empty) adapter and layout manager of the comments recycler view
        val commentsAdapter = CommentsAdapter()
        rvCommentsSlots.isNestedScrollingEnabled = false
        rvCommentsSlots.adapter = commentsAdapter
        rvCommentsSlots.layoutManager = LinearLayoutManager(context)

        // Hide the fab when scrolling the fragment
        nsvProfile.setOnScrollChangeListener {
            nestedScrollView: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            val fab = activity.findViewById(R.id.fab) as FloatingActionButton
            if (scrollY - oldScrollY > 0) fab.hide()
            else fab.show()
        }

        // If we don't have an internet connection, show an error and return
        if (!NetUtils.isConnectionAvailable(context)) {
            ErrorUtils.showNoConnectionToast(context)
            return
        }

        // Load the user in another thread
        object : AsyncTask<Void, Void, User?>() {
            override fun doInBackground(vararg params: Void?): User? {
                return NowbeUserData(token).getUser()
            }

            override fun onPostExecute(user: User?) {
                if (user != null) {
                    // Update the profile information
                    pivProfileInfo.updateInformation(user)

                    // Update the likes, couple and education info
                    pleLikesEducationCouple.updateInformation(user)

                    // Set up the pictures slots
                    psvPicturesSlots.updateSlots(user)

                    // Add the non-null comments to the adapter
                    for (comment in user.commentsSlots) {
                        if (comment?.data != "") {
                            commentsAdapter.addComment(comment!!)
                        }
                    }
                } else {
                    // We have no connection or the server is down
                    ErrorUtils.showNoConnectionToast(context)
                }
            }
        }.execute()
    }
}
