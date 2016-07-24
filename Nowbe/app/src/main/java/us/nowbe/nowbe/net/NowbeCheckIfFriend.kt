package us.nowbe.nowbe.net

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeCheckIfFriend(val userToken: String, val friendToken: String) : NowbeRequest() {
    /**
     * Attempts to add the user of the provided token and returns whether it was or not possible
     */
    fun check(): String {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getObjectFromResponse(response)

        // Get the string indicating the relationship
        val relation = json.getString(ApiUtils.API_SUCCESS)

        return relation
    }

    override fun getBody(): FormBody {
        // Build the body with 
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN_1, userToken)
                .add(ApiUtils.KEY_TOKEN_2, friendToken)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_CHECK_IF_FRIENDS_URL
    }
}