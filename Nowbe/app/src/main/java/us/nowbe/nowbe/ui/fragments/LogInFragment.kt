package us.nowbe.nowbe.ui.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.async.LoginObservable
import us.nowbe.nowbe.ui.activities.LandingActivity
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.NetUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class LogInFragment : Fragment() {
    /**
     * Previous subscription
     */
    var previousSubscription: Subscription? = null
        set(value) {
            // Unsubscribe the previous subscription before overriding it
            field?.unsubscribe()
            field = value
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_login, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLetMeIn.setOnClickListener({
            // If we don't have an internet connection, show an error and return
            if (!NetUtils.isConnectionAvailable(context)) {
                Toast.makeText(context, getString(R.string.general_no_internet), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val username = etLoginUsername.text.toString()
            val password = etLoginPassword.text.toString()

            // Check if the edit text fields are empty and if so, show an error
            if (username.isEmpty()) {
                etLoginUsername.error = getString(R.string.login_sign_up_error_user)
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etLoginPassword.error = getString(R.string.login_sign_up_error_password)
                return@setOnClickListener
            }

            // Attempt to login with the username and password provided in another thread
            previousSubscription = LoginObservable.create(username, password).subscribe(
                    // On Next
                    {
                        token ->
                        // If the login is good save the token
                        SharedPreferencesUtils.setLoggedIn(context, true)
                        SharedPreferencesUtils.setToken(context, token)

                        // And show the landing activity
                        val landingIntent = Intent(context, LandingActivity::class.java)

                        // Clear the activity stack
                        landingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(landingIntent)
                    },
                    // On Error
                    {
                        error ->

                        if (error is RequestNotSuccessfulException) {
                            // Show an error showing that the data provided is wrong
                            ErrorUtils.showWrongDataDialog(context)
                        } else {
                            // Show an error dialog indicating that we have no connection otherwise
                            ErrorUtils.showNoConnectionDialog(context)
                        }

                        // Log the error anyway
                        Log.e(LogInFragment::class.java.canonicalName, error.message)
                    })
        })
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}
