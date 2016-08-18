package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.Activity
import us.nowbe.nowbe.model.exceptions.EmptyActivityException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeActivityData(val token: String) : NowbeRequest() {
    /**
     * Attempts to get the activity data from the user
     */
    fun getActivity(): Activity {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getArrayFromResponse(response)

        // If the JSON is empty (no results) throw an empty feed exception
        if (json.length() <= 0) throw EmptyActivityException()

        // Get the object from the response and check whether it was or not successful
        return Activity.fromJson(json)
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.ACTIVITY_DATA_URL
    }
}