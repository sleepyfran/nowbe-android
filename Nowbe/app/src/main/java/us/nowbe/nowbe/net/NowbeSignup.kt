package us.nowbe.nowbe.net

import android.content.Context
import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeSignup(val context: Context, val user: String, val email: String, val password: String) : NowbeRequest() {

    /**
     * Attempts to sign up the user into Nowbe and returns whether it was or not succesful
     */
    fun attemptSignUp(): Boolean {
        // Make th request and get the JSON data returned
        val json = super.makeRequest()
        val success = json.getString(ApiUtils.API_SUCCESS) == ApiUtils.API_SUCCESS_OK

        // If it was successful, set the user as logged in and save the token
        if (success) {
            SharedPreferencesUtils.setLoggedIn(context, true)
            SharedPreferencesUtils.setToken(context, json.getString(ApiUtils.API_TOKEN))
        }

        // TODO: Return different states based on whether the user already exists or not
        return success
    }

    override fun getBody(): FormBody {
        // Build the body with an user, password and OS
        return FormBody.Builder()
                .add(ApiUtils.KEY_NICKNAME, user)
                .add(ApiUtils.KEY_EMAIL, email)
                .add(ApiUtils.KEY_PASSWORD, password)
                .add(ApiUtils.KEY_OS, ApiUtils.OS)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.SIGNUP_URL
    }
}