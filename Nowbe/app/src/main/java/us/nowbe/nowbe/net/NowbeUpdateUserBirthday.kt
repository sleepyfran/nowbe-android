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

class NowbeUpdateUserBirthday(val token: String, val date: String) : NowbeRequest() {
    /**
     * Attempts to update the birthday of the user and returns whether it was possible or not
     */
    fun update() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user and the age
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_AGE, date)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_BIRTHDAY_URL
    }
}