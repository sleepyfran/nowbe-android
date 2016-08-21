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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_sign_up.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.model.exceptions.UserAlreadyExistsException
import us.nowbe.nowbe.net.async.SignupObservable
import us.nowbe.nowbe.ui.activities.LandingActivity
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.NetUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils
import us.nowbe.nowbe.utils.ValidatorUtils
import java.io.IOException

class SignupFragment : Fragment() {
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

            val user = etSignupUsername.text.toString()
            val email = etSignupEmail.text.toString()
            val password = etSignupPassword.text.toString()
            val passwordVerif = etSignupPasswordVerif.text.toString()

            // Check if the edit text fields are empty and if so, show an error
            if (user.isEmpty()) {
                etSignupUsername.error = getString(R.string.login_sign_up_error_user)
                return@setOnClickListener
            }

            if (email.isEmpty() || !ValidatorUtils.isEmailValid(email)) {
                etSignupEmail.error = getString(R.string.login_sign_up_error_email)
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etSignupPassword.error = getString(R.string.login_sign_up_error_password)
                return@setOnClickListener
            }

            if (passwordVerif.isEmpty() || password != passwordVerif) {
                etSignupPasswordVerif.error = getString(R.string.login_sign_up_error_password)
                return@setOnClickListener
            }

            // Attempt to login with the username and password provided in another thread
            previousSubscription = SignupObservable.create(user, email, password).subscribe(
                    // On Next
                    {
                        token ->

                        // Save the token of the user
                        SharedPreferencesUtils.setLoggedIn(context, true)
                        SharedPreferencesUtils.setToken(context, token)
                        SharedPreferencesUtils.setUsername(context, user)

                        // Show the landing activity
                        val landingIntent = Intent(context, LandingActivity::class.java)

                        // Clear the activity stack
                        landingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(landingIntent)
                    },
                    // On error
                    {
                        error ->

                        if (error is UserAlreadyExistsException) {
                            // Show an error dialog when the user/mail already exists
                            ErrorUtils.showUserAlreadyExistsDialog(context)
                        } else if (error is RequestNotSuccessfulException) {
                            // Show an error dialog when the sign up went wrong
                            ErrorUtils.showGeneralWhoopsDialog(context)
                        } else if (error is IOException) {
                            // Show an error dialog indicating that we have no connection otherwise
                            ErrorUtils.showNoConnectionDialog(context)
                        }
                    }
            )
        })
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}
