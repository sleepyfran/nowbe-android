package us.nowbe.nowbe.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.activities.LandingActivity
import us.nowbe.nowbe.net.NowbeLogin
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.NetUtils

class LogInFragment : Fragment {
    constructor() : super()

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

            // Boolean specifying if there was or not an error
            var error = false

            // Check if the edit text fields are empty and if so, show an error
            if (username.isEmpty()) {
                etLoginUsername.error = getString(R.string.login_sign_up_error_user)
                error = true
            }

            if (password.isEmpty()) {
                etLoginPassword.error = getString(R.string.login_sign_up_error_password)
                error = true
            }

            // Attempt to login with the username and password provided in another thread
            if (!error) {
                object : AsyncTask<Void, Void, ApiUtils.Companion.LoginResults>() {
                    override fun doInBackground(vararg p0: Void?): ApiUtils.Companion.LoginResults {
                        return NowbeLogin(context, username, password).attemptLogin()
                    }

                    override fun onPostExecute(result: ApiUtils.Companion.LoginResults?) {
                        if (result == ApiUtils.Companion.LoginResults.LOGGED) {
                            // If the login is good, show the landing activity
                            val landingIntent = Intent(context, LandingActivity::class.java)

                            // Clear the activity stack
                            landingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(landingIntent)
                        } else if (result == ApiUtils.Companion.LoginResults.WRONG_DATA) {
                            // Show an error showing that the data provided is wrong
                            AlertDialog.Builder(activity)
                                    .setTitle(getString(R.string.login_sign_up_error_title))
                                    .setMessage(getString(R.string.login_sign_up_error_login_message))
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show()
                        } else {
                            // Show an error dialog indicating that we have no connection otherwise
                            AlertDialog.Builder(activity)
                                    .setTitle(getString(R.string.login_sign_up_error_title))
                                    .setMessage(getString(R.string.login_sign_up_error_connection))
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show()
                        }
                    }
                }.execute()
            }
        })
    }
}
