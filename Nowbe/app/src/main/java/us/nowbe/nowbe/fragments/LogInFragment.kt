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

import us.nowbe.nowbe.R
import us.nowbe.nowbe.activities.LandingActivity
import us.nowbe.nowbe.net.NowbeLogin

class LogInFragment : Fragment {
    constructor() : super()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_login, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLetMeIn.setOnClickListener({
            val username = etLoginUsername.text.toString()
            val password = etLoginPassword.text.toString()

            // Check if the edit text fields are empty and if so, show an error
            if (username.isEmpty()) etLoginUsername.error = getString(R.string.login_sign_up_error_user)
            if (password.isEmpty()) etLoginPassword.error = getString(R.string.login_sign_up_error_password)

            // Attempt to login with the username and password provided in another thread
            object : AsyncTask<Void, Void, Boolean>() {
                override fun doInBackground(vararg params: Void?): Boolean {
                    return NowbeLogin(context, username, password).attemptLogin()
                }

                override fun onPostExecute(result: Boolean?) {
                    if (result!!) {
                        // If the login is good, show the landing activity
                        startActivity(Intent(context, LandingActivity::class.java))
                    } else {
                        // Show an error dialog otherwise
                        AlertDialog.Builder(activity)
                                .setTitle(getString(R.string.login_sign_up_error_title))
                                .setMessage(getString(R.string.login_sign_up_error_login_message))
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                    }
                }
            }.execute()
        })
    }
}
