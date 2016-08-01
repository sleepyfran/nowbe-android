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

class NowbeUpdateUserBirthday(val token: String, val date: String) : NowbeRequest() {
    /**
     * Attempts to update the birthday of the user and returns whether it was possible or not
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
        // Build the body with the token of the user and the age
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_AGE, date)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_BIRTHDAY_URL
    }
}