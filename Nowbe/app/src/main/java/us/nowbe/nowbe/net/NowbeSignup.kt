package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.model.exceptions.UserAlreadyExistsException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeSignup(val user: String, val email: String, val password: String) : NowbeRequest() {

    /**
     * Attempts to sign up the user into Nowbe and returns whether it was or not succesful
     */
    fun attemptSignUp(): String {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getObjectFromResponse(response)
        val success = json.getString(ApiUtils.API_SUCCESS)

        when (success) {
            ApiUtils.API_SUCCESS_EXIST -> throw UserAlreadyExistsException("That user already exists in the database.")
            ApiUtils.API_SUCCESS_ERROR -> throw RequestNotSuccessfulException("We got a 0 over here.")
        }

        // Get the token
        val token = json.getString(ApiUtils.API_USER_TOKEN)

        return token
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