package us.nowbe.nowbe.ui.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Intent
import android.net.Uri
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
import us.nowbe.nowbe.model.exceptions.UserDoesNotExistsException
import us.nowbe.nowbe.net.async.SayHelloObservable
import us.nowbe.nowbe.net.async.UserDataObservable
import us.nowbe.nowbe.utils.*

class ProfileFragment : Fragment() {
    /**
     * Token to which the profile refers to
     */
    lateinit var profileToken: String

    /**
     * Interface to call when we got a result from the API call
     */
    var onUserResult: Interfaces.OnUserResult? = null

    companion object {
        /**
         * Returns a new instance with the specified token as the one to use
         */
        fun newInstance(token: String, onUserResult: Interfaces.OnUserResult?): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.profileToken = token
            fragment.onUserResult = onUserResult
            return fragment
        }
    }

    /**
     * Method that will setup the say hello button
     */
    fun setupSayHelloButton(user: User) {
        btnSayHello.setOnClickListener({
            // Get the token from the app user
            val appUserToken = SharedPreferencesUtils.getToken(context)!!

            // Send the hello!
            SayHelloObservable.create(appUserToken, user.token).subscribe(
                    // On Next
                    {
                        // Show a toast indicating that we sent the hello
                        Toast.makeText(context,
                                getString(R.string.profile_hello_sent, user.fullName),
                                Toast.LENGTH_SHORT).show()
                    },
                    // On Error
                    {
                        ErrorUtils.showNoConnectionToast(context)
                    }
            )
        })
    }

    /**
     * Method that will setup the send email button
     */
    fun setupSendEmailButton(user: User) {
        btnSendMessage.setOnClickListener({
            // Ask the user which app to use sending the mail
            val sendEmailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + user.email))
            startActivity(Intent.createChooser(sendEmailIntent, null))
        })
    }

    /**
     * Method that loads the user data in another thread and updates the displayed data
     */
    fun loadUserData(forceRefresh: Boolean) {
        // Get the token of the user
        val userToken = SharedPreferencesUtils.getToken(context)!!

        UserDataObservable.create(userToken, profileToken).subscribe(
                // On Next
                {
                    user ->
                    // Hide the refresh icon
                    srlProfileRefresh.isRefreshing = false

                    // Callback indicating the result we got if the callback is not null
                    onUserResult?.onUserResult(user)

                    // Update the profile information
                    pivProfileInfo.updateInformation(user, forceRefresh)

                    // Update the likes, couple and education info
                    pleLikesEducationCouple.updateInformation(user)

                    // Set up the pictures slots
                    psvPicturesSlots.updateSlots(user)

                    // TODO: Show the full picture when clicking on a slot
                    psvPicturesSlots.onClick = object : Interfaces.OnPictureSlotClick {
                        override fun onPictureSlotClick(itemSelected: Int) {
                            Toast.makeText(context, itemSelected.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Add the comments to the adapter
                    csvCommentsSlots.updateSlots(user)

                    // TODO: Show the full comment when clicking on a comment
                    csvCommentsSlots.onClick = object : Interfaces.OnCommentSlotClick {
                        override fun onCommentSlotClick(itemSelected: Int) {
                            Toast.makeText(context, itemSelected.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    // Setup the send hello and send email if they're visible
                    if (btnSayHello.visibility == View.VISIBLE) {
                        setupSayHelloButton(user)
                        setupSendEmailButton(user)
                    }
                },
                // On Error
                {
                    error ->

                    // If the user doesn't exists
                    if (error is UserDoesNotExistsException) {
                        ErrorUtils.showUserDoesNotExists(context)
                    } else {
                        ErrorUtils.showNoConnectionDialog(context)
                    }
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retain the instance of the fragment so it survives configuration changes
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_profile, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // If the user is the one using the app, hide the send mail and hello buttons
        if (ApiUtils.isAppUser(context, profileToken)) {
            btnSayHello.visibility = View.GONE
            btnSendMessage.visibility = View.GONE
        }

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
        loadUserData(false)

        // Implement the swipe to refresh feature
        srlProfileRefresh.setOnRefreshListener {
            // Refresh the user data
            loadUserData(true)
        }
    }
}
