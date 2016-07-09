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
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_welcome.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.activities.LandingActivity
import us.nowbe.nowbe.net.NowbeLogin
import us.nowbe.nowbe.net.NowbeSignup

class SignUpFragment : Fragment {
    constructor() : super()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_sign_up, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener({
            val user = etSignupUsername.text.toString()
            val email = etSignupEmail.text.toString()
            val password = etSignupPassword.text.toString()

            // Check if the edit text fields are empty and if so, show an error
            if (user.isEmpty()) etSignupUsername.error = getString(R.string.login_sign_up_error_user)
            if (email.isEmpty()) etSignupEmail.error = getString(R.string.login_sign_up_error_email)
            if (password.isEmpty()) etSignupPassword.error = getString(R.string.login_sign_up_error_password)

            // Attempt to login with the username and password provided in another thread
            object : AsyncTask<Void, Void, Boolean>() {
                override fun doInBackground(vararg params: Void?): Boolean {
                    return NowbeSignup(context, user, email, password).attemptSignUp()
                }

                override fun onPostExecute(result: Boolean?) {
                    if (result!!) {
                        // If the sign up is good, show the landing activity
                        startActivity(Intent(context, LandingActivity::class.java))
                    } else {
                        // Show an error dialog otherwise
                        AlertDialog.Builder(activity)
                                .setTitle(getString(R.string.login_sign_up_error_title))
                                .setMessage(getString(R.string.login_sign_up_error_sign_up_message))
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                    }
                }
            }.execute()
        })
    }
}
