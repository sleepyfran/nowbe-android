package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.model.exceptions.UserDoesNotExistsException
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.NumberUtils
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeUserData(val token: String) : NowbeRequest() {

    /**
     * Attempts to get an user by its token
     */
    fun getUser(): User {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getFirstObjectFromArray(response)

        if (json.length() <= 0) throw UserDoesNotExistsException("Hello, is there anybody in there?")

        // Return the user if the request was successful or null otherwise
        return User.fromJson(token, json)
    }

    override fun getBody(): FormBody {
        // Make the body with the token
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_DATA_URL
    }
}