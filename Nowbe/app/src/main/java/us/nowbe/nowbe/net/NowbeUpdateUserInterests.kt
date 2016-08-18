package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeUpdateUserInterests(val token: String, val newInterests: String) : NowbeRequest() {
    /**
     * Attempts to updates the interests of the user
     */
    fun update(): Boolean {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()

        // Get the object from the response and check whether it was or not successful
        val json = super.getObjectFromResponse(response)
        val success = json.getInt(ApiUtils.API_SUCCESS)

        if (success != 1) throw RequestNotSuccessfulException("We got a 0 here. Not good")

        return true
    }

    override fun getBody(): FormBody {
        // No whitespaces
        val noWhitespacesInterests = newInterests.replace(" ","")

        // Build the body with the token and the new interests
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_INTERESTS, noWhitespacesInterests)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_INTERESTS_URL
    }
}