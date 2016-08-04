package us.nowbe.nowbe.net

import okhttp3.FormBody
import org.json.JSONArray
import org.json.JSONException
import us.nowbe.nowbe.model.Feed
import us.nowbe.nowbe.model.exceptions.EmptyFeedException
import us.nowbe.nowbe.utils.ApiUtils
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeFeedData(val token: String): NowbeRequest() {
    /**
     * Attemps to get the feed of the user
     */
    fun getFeed(forceRefresh: Boolean): Feed {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getArrayFromResponse(response)

        // If the JSON is empty (no results) throw an empty feed exception
        if (json.length() <= 0) throw EmptyFeedException("This seems a little empty, m8")

        // Otherwise return the feed from the JSON we got
        return Feed.fromJson(json, forceRefresh)
    }

    override fun getBody(): FormBody {
        // Build the body with the token
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_FEED_URL
    }
}