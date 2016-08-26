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

class NowbeUpdateUserCouple(val userToken: String, val coupleToken: String) : NowbeRequest() {
    /**
     * Attempts to update the couple of the user
     */
    fun updateCouple() {
        val response = super.makeRequest()

        if (response == "0") throw RequestNotSuccessfulException()
    }

    override fun getBody(): FormBody {
        // Build the body with the user's token and the couple's token
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, userToken)
                .add(ApiUtils.KEY_COUPLE, coupleToken)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_COUPLE_URL
    }
}