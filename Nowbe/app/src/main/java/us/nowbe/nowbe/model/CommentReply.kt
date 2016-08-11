package us.nowbe.nowbe.model

import org.json.JSONObject
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentReply(token: String,
                   fullName: String,
                   username: String,
                   profilePic: String,
                   text: String,
                   timestamp: String) {

    companion object {
        /**
         * Builds a CommentReply object from JSON data
         */
        fun fromJson(json: JSONObject): CommentReply {
            val token = json.getString(ApiUtils.KEY_REPLY_TOKEN)
            val fullName = json.getString(ApiUtils.API_USER_FULLNAME)
            val username = json.getString(ApiUtils.API_USER_USERNAME).toLowerCase()
            val profilePic = ApiUtils.getThumbProfilePicDir(json.getString(ApiUtils.API_USER_PROFILE_PIC_ALT), false)
            val text = json.getString(ApiUtils.KEY_REPLY)
            val timestamp = json.getString(ApiUtils.KEY_TIMESTAMP)

            return CommentReply(token, fullName, username, profilePic, text, timestamp)
        }
    }

    /**
     * A Nowbe feedback content representation
     */
    val token: String
    val fullName: String
    val username: String
    val profilePic: String
    val text: String
    val timestamp: String

    init {
        this.token = token
        this.fullName = fullName
        this.username = username
        this.profilePic = profilePic
        this.text = text
        this.timestamp = timestamp
    }
}