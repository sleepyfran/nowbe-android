package us.nowbe.nowbe.model

import org.json.JSONObject
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class FeedContent(token: String,
                  profilePic: String,
                  isAvailable: Boolean,
                  fullname: String,
                  username: String,
                  update: String,
                  timestamp: String,
                  forceRefresh: Boolean) {
    /**
     * A Nowbe feed content representation
     */
    val token: String
    val profilePic: String
    val isAvailable: Boolean
    val fullname: String
    val username: String
    val update: String
    val timestamp: String
    val forceRefresh: Boolean

    init {
        this.token = token
        this.profilePic = profilePic
        this.isAvailable = isAvailable
        this.fullname = fullname
        this.username = username.toLowerCase()
        this.update = update
        this.timestamp = timestamp
        this.forceRefresh = forceRefresh
    }

    companion object {
        /**
         * Returns a FeedContent from a JSON object
         */
        fun fromJson(jsonObject: JSONObject, forceRefresh: Boolean): FeedContent {
            // Get the info from the provided JSON object and return a FeedContent object from it
            val token = jsonObject.getString(ApiUtils.API_USER_TOKEN)
            val profilePic = jsonObject.getString(ApiUtils.API_FEED_PICTURE)
            val isAvailable = jsonObject.getInt(ApiUtils.API_USER_STATUS) == 1
            val fullname = jsonObject.getString(ApiUtils.API_USER_FULLNAME)
            val username = jsonObject.getString(ApiUtils.API_FEED_USERNAME)
            val update = jsonObject.getString(ApiUtils.API_FEED_UPDATE)
            val timestamp = jsonObject.getString(ApiUtils.API_TIMESTAMP)

            return FeedContent(token, profilePic, isAvailable, fullname, username, update, timestamp, forceRefresh)
        }
    }
}