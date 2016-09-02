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

class NowbeUpdateOnline(val token: String, val state: Int) : NowbeRequest() {
    /**
     * Attempts to update the online state of the user
     */
    fun updateOnline() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the state
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_STATE, state.toString())
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.UPDATE_ONLINE_URL
    }
}