package us.nowbe.nowbe.ui.activities

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
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_base_no_tabs.*
import rx.Observable
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.ui.fragments.ProfileFragment
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.NowbeCheckIfFriend
import us.nowbe.nowbe.net.async.AddUserObservable
import us.nowbe.nowbe.net.async.CheckIfFriendObservable
import us.nowbe.nowbe.net.async.RemoveUserObservable
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class ProfileActivity : BaseActivity() {
    /**
     * Implementation to call on user result
     */
    val onUserResult: Interfaces.OnUserResult by lazy { OnUserResult(applicationContext, fab) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the toolbar
        toolbar.visibility = View.GONE

        // Get the token from the intent's extras
        val token = intent.getStringExtra(ApiUtils.API_USER_TOKEN)

        // Load the profile fragment with the specified token
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment, ProfileFragment.newInstance(token, onUserResult))
                .commit()
    }

    override fun hasTabs(): Boolean {
        return false
    }

    override fun onDestroy() {
        (onUserResult as OnUserResult).previousSubscription?.unsubscribe()
        super.onDestroy()
    }

    /**
     * Implementation of the on user result
     */
    class OnUserResult(val context: Context, val fab: FloatingActionButton) : Interfaces.OnUserResult {
        /**
         * Previous subscription
         */
        var previousSubscription: Subscription? = null
            set(value) {
                // Unsubscribe the previous subscription before overriding it
                field?.unsubscribe()
                field = value
            }

        /**
         * Updates the fab with the add button if the users are not friends or the remove button if they are
         */
        fun updateFabAreFriends(areFriends: Boolean) {
            if (areFriends) {
                fab.setImageDrawable(context.resources.getDrawable(R.drawable.ic_delete_white))
            } else {
                fab.setImageDrawable(context.resources.getDrawable(R.drawable.ic_add_white))
            }
        }

        /**
         * Setups the on click listener of the fab to add the user
         */
        fun setupFabAddUser(appUserToken: String, user: User) {
            // Update the drawable with the add button
            updateFabAreFriends(false)

            // Add the friend when the user clicks the fab
            fab.setOnClickListener {
                previousSubscription = AddUserObservable.create(appUserToken, user.token).subscribe(
                        // On Next
                        {
                            result ->

                            // If the user was added, show a toast and update the fab
                            Toast.makeText(context, context.getString(R.string.profile_user_added, user.fullName), Toast.LENGTH_LONG).show()
                            setupFabRemoveUser(appUserToken, user)
                        },
                        // On Error
                        {
                            error ->

                            if (error is RequestNotSuccessfulException) {
                                ErrorUtils.showUserNotAddedToast(context)
                            } else {
                                ErrorUtils.showNoConnectionDialog(context)
                            }
                        }
                )
            }
        }

        /**
         * Setups the on click listener of the fab to remove the user
         */
        fun setupFabRemoveUser(appUserToken: String, user: User) {
            // Update the drawable with the remove button
            updateFabAreFriends(true)

            // Remove the friend when the user clicks the fab
            fab.setOnClickListener({
                previousSubscription = RemoveUserObservable.create(appUserToken, user.token).subscribe(
                        // On Next
                        {
                            result ->

                            // If the user was removed, show a toast and update the fab
                            Toast.makeText(context, context.getString(R.string.profile_user_removed, user.fullName), Toast.LENGTH_LONG).show()
                            setupFabAddUser(appUserToken, user)
                        },
                        // On Error
                        {
                            error ->

                            if (error is RequestNotSuccessfulException) {
                                ErrorUtils.showUserNotRemovedToast(context)
                            } else {
                                ErrorUtils.showNoConnectionDialog(context)
                            }
                        }
                )
            })
        }

        override fun onUserResult(user: User) {
            // Check whether the user is the one using the app
            if (!ApiUtils.isAppUser(context, user.token)) {
                // Take the token from the user using the app
                val appUserToken = SharedPreferencesUtils.getToken(context)!!

                // Check if they're friends async
                previousSubscription = CheckIfFriendObservable.create(appUserToken, user.token).subscribe(
                        // On Next
                        {
                            relation ->

                            if (ApiUtils.hasRelation(relation)) {
                                // Show the remove icon on the fab
                                setupFabRemoveUser(appUserToken, user)
                            } else {
                                // If they're not friends show the add fab
                                setupFabAddUser(appUserToken, user)
                            }
                        }
                )
            }
        }
    }
}