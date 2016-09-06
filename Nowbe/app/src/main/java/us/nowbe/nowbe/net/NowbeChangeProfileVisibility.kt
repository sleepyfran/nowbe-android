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

class NowbeChangeProfileVisibility(val token: String, val status: Int) : NowbeRequest() {
    /**
     * Attempts to update the visibility of the user
     */
    fun change() {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the new status
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_STATE, status.toString())
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.CHANGE_PROFILE_VISIBILITY_URL
    }
}