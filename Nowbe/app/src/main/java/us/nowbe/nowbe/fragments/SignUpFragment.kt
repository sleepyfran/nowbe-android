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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.activities.LandingActivity
import us.nowbe.nowbe.net.NowbeSignup
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.NetUtils
import us.nowbe.nowbe.utils.ValidatorUtils

class SignUpFragment : Fragment {
    constructor() : super()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_sign_up, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener({
            // If we don't have an internet connection, show an error and return
            if (!NetUtils.isConnectionAvailable(context)) {
                Toast.makeText(context, getString(R.string.general_no_internet), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Boolean specifying if there was or not an error
            var error = false

            val user = etSignupUsername.text.toString()
            val email = etSignupEmail.text.toString()
            val password = etSignupPassword.text.toString()
            val passwordVerif = etSignupPasswordVerif.text.toString()

            // Check if the edit text fields are empty and if so, show an error
            if (user.isEmpty()) {
                etSignupUsername.error = getString(R.string.login_sign_up_error_user)
                error = true
            }

            if (email.isEmpty() || !ValidatorUtils.isEmailValid(email)) {
                etSignupEmail.error = getString(R.string.login_sign_up_error_email)
                error = true
            }

            if (password.isEmpty()) {
                etSignupPassword.error = getString(R.string.login_sign_up_error_password)
                error = true
            }

            if (passwordVerif.isEmpty() || password != passwordVerif) {
                etSignupPasswordVerif.error = getString(R.string.login_sign_up_error_password)
                error = true
            }

            // Attempt to login with the username and password provided in another thread
            if (!error) {
                object : AsyncTask<Void, Void, ApiUtils.Companion.SignupResults>() {
                    override fun doInBackground(vararg params: Void?): ApiUtils.Companion.SignupResults {
                        return NowbeSignup(context, user, email, password).attemptSignUp()
                    }

                    override fun onPostExecute(result: ApiUtils.Companion.SignupResults?) {
                        if (result == ApiUtils.Companion.SignupResults.OK) {
                            // If the sign up is good, show the landing activity
                            val landingIntent = Intent(context, LandingActivity::class.java)

                            // Clear the activity stack
                            landingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(landingIntent)
                        } else if (result == ApiUtils.Companion.SignupResults.NOT_OK) {
                            // Show an error dialog when the sign up went wrong
                            AlertDialog.Builder(activity)
                                    .setTitle(getString(R.string.login_sign_up_error_title))
                                    .setMessage(getString(R.string.login_sign_up_error_sign_up_message))
                                    .setPositiveButton(android.R.string.ok, null)
                                    .show()
                        } else if (result == ApiUtils.Companion.SignupResults.EXISTS) {
                            // Or another when the user already exits
                            AlertDialog.Builder(activity)
                                    .setTitle(getString(R.string.login_sign_up_error_title))
                                    .setMessage(getString(R.string.login_sign_up_error_sign_up_exist_message))
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
