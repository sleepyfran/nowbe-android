package us.nowbe.nowbe.net

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import okhttp3.FormBody
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeRemoveUser(val tokenOfUser: String, val tokenToRemove: String) : NowbeRequest() {
    /**
     * Attempts to add the user of the provided token and returns whether it was or not possible
     */
    fun remove(): String {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()

        // Get the object from the response
        val json = super.getObjectFromResponse(response)
        val success = json.getString(ApiUtils.API_SUCCESS)

        if (success != ApiUtils.API_SUCCESS_OK) throw RequestNotSuccessfulException("Couldn't remove the user")

        return success
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user and the token of the user to remove
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN_1, tokenOfUser)
                .add(ApiUtils.KEY_TOKEN_2, tokenToRemove)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_REMOVE_FRIEND_URL
    }
}