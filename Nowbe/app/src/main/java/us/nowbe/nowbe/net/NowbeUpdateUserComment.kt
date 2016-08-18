package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeUpdateUserComment(val token: String, val commentIndex: Int, val comment: String) : NowbeRequest() {
    /**
     * Attempts to update the comment of an user
     */
    fun updateComment(): Boolean {
        // Make the request and get the JSON data returned
        super.makeRequest()

        // Get the object from the response and check whether it was or not successful
        //val json = super.getObjectFromResponse(response)
        //val success = json.getInt(ApiUtils.API_SUCCESS)
        //if (success != 1) throw RequestNotSuccessfulException()

        return true
    }

    override fun getBody(): FormBody {
        // Build the body with TODO
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_COMMENT_INDEX, commentIndex.toString())
                .add(ApiUtils.KEY_COMMENT_DATA, comment)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_COMMENT_URL
    }
}