package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeRemoveActivity(val token: String, val id: String) : NowbeRequest() {
    /**
     * Attempts to remove an activity identified by the id specified
     */
    fun remove() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the id
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_ACTIVITY_ID, id)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.ACTIVITY_DELETE_ONE_URL
    }
}