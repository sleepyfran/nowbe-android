package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeSetTutorialSeen(val userToken: String) : NowbeRequest() {
    /**
     * Attempts to sets the tutorial as seen
     */
    fun setTutorial() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token of the user
        return FormBody.Builder()
                .add(ApiUtils.KEY_USER_TOKEN, userToken)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.SET_TUTORIAL_SEEN_URL
    }
}