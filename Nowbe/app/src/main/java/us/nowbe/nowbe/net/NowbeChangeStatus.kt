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

class NowbeChangeStatus(val token: String, val isAvailable: Boolean) : NowbeRequest() {
    /**
     * Attempts to change the status of the user
     */
    fun changeStatus() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        val status: String
        if (isAvailable) status = "1"
        else status = "0"

        // Build the body with the token and the new status
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_STATUS, status)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.CHANGE_STATUS_URL
    }
}