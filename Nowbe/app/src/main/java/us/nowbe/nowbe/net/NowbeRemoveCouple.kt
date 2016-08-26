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

class NowbeRemoveCouple(val userToken: String) : NowbeRequest() {
    /**
     * Attempts to remove the couple of the user
     */
    fun removeCouple() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, userToken)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_REMOVE_COUPLE_URL
    }
}