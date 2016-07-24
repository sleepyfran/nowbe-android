package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeSayHello(val tokenFrom: String, val tokenTo: String) : NowbeRequest() {
    /**
     * Attemps to say hello to an user
     */
    fun sayHello() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user and the token of the user to say hello
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN_TO, tokenTo)
                .add(ApiUtils.KEY_TOKEN_FROM, tokenFrom)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_SAY_HELLO_URL
    }
}