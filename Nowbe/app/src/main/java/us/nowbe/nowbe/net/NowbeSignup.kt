package us.nowbe.nowbe.net

import android.content.Context
import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils
import java.net.ConnectException
import java.net.UnknownHostException

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
    fun attemptSignUp(): ApiUtils.Companion.SignupResults {
        // Make the request and get the JSON data returned
        val response: String

        // Attempt to make a request
        try {
            response = super.makeRequest()
        } catch (e: UnknownHostException) {
            return ApiUtils.Companion.SignupResults.NO_CONNECTION
        }catch (e: ConnectException) {
            return ApiUtils.Companion.SignupResults.NO_CONNECTION
        }

        // Make th request and get the JSON data returned
        val json = super.getObjectFromResponse(response)
        var success = ApiUtils.Companion.SignupResults.NOT_OK

        if (json.getString(ApiUtils.API_SUCCESS) == ApiUtils.API_SUCCESS_OK) {
            success = ApiUtils.Companion.SignupResults.OK
        } else if (json.getString(ApiUtils.API_SUCCESS) == ApiUtils.API_SUCCESS_ERROR) {
            success = ApiUtils.Companion.SignupResults.NOT_OK
        } else if (json.getString(ApiUtils.API_SUCCESS) == ApiUtils.API_SUCCESS_EXIST) {
            success = ApiUtils.Companion.SignupResults.EXISTS
        }

        // If it was successful, set the user as logged in and save the token
        if (success == ApiUtils.Companion.SignupResults.OK) {
            SharedPreferencesUtils.setLoggedIn(context, true)
            SharedPreferencesUtils.setToken(context, json.getString(ApiUtils.API_USER_TOKEN))
        }

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