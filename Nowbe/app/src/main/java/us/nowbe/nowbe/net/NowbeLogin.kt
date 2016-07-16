package us.nowbe.nowbe.net

import android.content.Context
import android.util.Log
import okhttp3.FormBody
import okhttp3.MediaType
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeLogin(val context: Context, val username: String, val password: String) : NowbeRequest() {
    /**
     * Attempts to log into Nowbe and returns whether it was or not successful
     */
    fun attemptLogin(): ApiUtils.Companion.LoginResults {
        // Make the request and get the JSON data returned
        val response: String

        // Attempt to make a request
        try {
            response = super.makeRequest()
        } catch (e: UnknownHostException) {
            return ApiUtils.Companion.LoginResults.NO_CONNECTION
        } catch (e: ConnectException) {
            return ApiUtils.Companion.LoginResults.NO_CONNECTION
        }

        val json = super.getObjectFromResponse(response)

        val success = json.getString(ApiUtils.API_SUCCESS) == ApiUtils.API_SUCCESS_OK

        // If the log in was successful, save the token and set the user as logged in
        if (success) {
            SharedPreferencesUtils.setLoggedIn(context, true)
            SharedPreferencesUtils.setToken(context, json.getString(ApiUtils.API_USER_TOKEN))

            // Return an OK
            return ApiUtils.Companion.LoginResults.LOGGED
        } else {
            // Return a WRONG_DATA
            return ApiUtils.Companion.LoginResults.WRONG_DATA
        }
    }

    override fun getBody(): FormBody {
        // Build the body with an user, password and OS
        return FormBody.Builder()
                .add(ApiUtils.KEY_USER, username)
                .add(ApiUtils.KEY_PASSWORD, password)
                .add(ApiUtils.KEY_OS, ApiUtils.OS)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.LOGIN_URL
    }
}