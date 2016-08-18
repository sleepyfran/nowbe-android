package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.Feed
import us.nowbe.nowbe.model.exceptions.EmptyFeedException
import us.nowbe.nowbe.utils.ApiUtils

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
    fun getFeed(): Feed {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        val json = super.getArrayFromResponse(response)

        // If the JSON is empty (no results) throw an empty feed exception
        if (json.length() <= 0) throw EmptyFeedException()

        // Otherwise return the feed from the JSON we got
        return Feed.fromJson(json)
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