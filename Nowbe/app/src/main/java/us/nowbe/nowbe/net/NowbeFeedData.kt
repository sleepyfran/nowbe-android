package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.Feed
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
    fun getFeed(): Feed? {
        // Make the request and get the JSON data returned
        val response: String

        // Attempt to make a request
        try {
            response = super.makeRequest()
        } catch (e: UnknownHostException) {
            return null
        } catch (e: ConnectException) {
            return null
        }

        val json = super.getArrayFromResponse(response)

        // Return the feed from the JSON we got
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