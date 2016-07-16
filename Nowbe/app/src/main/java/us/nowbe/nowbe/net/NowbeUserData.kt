package us.nowbe.nowbe.net

import android.util.Log
import okhttp3.FormBody
import org.json.JSONException
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.ApiUtils
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
    fun getUser(): User? {
        // Make the request and get the JSON data returned
        val response: String

        // Attempt to make a request TODO: Check the null return
        try {
            response = super.makeRequest()
        } catch (e: UnknownHostException) {
            return null
        } catch (e: ConnectException) {
            return null
        }

        val json = super.getFirstObjectFromArray(response)
        val success = json.length() != 0

        // Return the user if the request was successful or null otherwise
        return if (success) User.fromJson(token, json) else null
    }

    override fun getBody(): FormBody {
        // Build the body with the token
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_DATA_URL
    }
}