package us.nowbe.nowbe.model

import org.json.JSONObject
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SearchResult(val token: String,
                   val profilePic: String,
                   val isPrivate: Boolean,
                   val fullname: String,
                   val username: String,
                   val about: String,
                   val isRelationMutual: Boolean) {

    companion object {
        /**
         * Returns a Search Result from a JSON object
         */
        fun fromJson(json: JSONObject): SearchResult {
            val token = json.getString(ApiUtils.KEY_TOKEN)
            val profilePic = "$token.jpg"
            val isPrivate = json.getInt(ApiUtils.KEY_PUBLIC) == 0
            val fullname = json.getString(ApiUtils.API_USER_FULLNAME)
            val username = json.getString(ApiUtils.API_USER_USERNAME)
            val about = json.getString(ApiUtils.KEY_SEARCH_ABOUT)
            val isRelationMutual = json.getString(ApiUtils.KEY_SEARCH_MUTUAL) == ApiUtils.API_FRIENDSHIP_MUTUAL

            return SearchResult(token, profilePic, isPrivate, fullname, username, about, isRelationMutual)
        }
    }

}