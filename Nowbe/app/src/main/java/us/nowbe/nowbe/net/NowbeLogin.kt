package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeLogin(val username: String, val password: String) : NowbeRequest() {
    /**
     * Attempts to log into Nowbe and returns whether it was or not successful
     */
    fun attemptLogin(): NowbeResponse<String> {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getObjectFromResponse(response)

        return NowbeResponse(json.getString(ApiUtils.API_SUCCESS) == ApiUtils.API_SUCCESS_OK,
                json.optString(ApiUtils.API_USER_TOKEN))
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