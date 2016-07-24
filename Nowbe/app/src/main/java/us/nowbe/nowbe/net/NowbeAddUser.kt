package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
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

class NowbeAddUser(val tokenOfUser: String, val tokenToAdd: String) : NowbeRequest() {
    /**
     * Attempts to add the user of the provided token and returns whether it was or not possible
     */
    fun add(): String {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()

        // Get the object from the response and check whether it was or not successful
        val json = super.getObjectFromResponse(response)
        val success = json.getString(ApiUtils.API_SUCCESS)

        if (success != ApiUtils.API_SUCCESS_OK) throw RequestNotSuccessfulException("Could not add that user!")

        return success
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user and the token of the user to add
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN_1, tokenOfUser)
                .add(ApiUtils.KEY_TOKEN_2, tokenToAdd)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_ADD_FRIEND_URL
    }
}