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

class NowbeNotifyAccessFromSearch(val visitantToken: String, val visitantNickname: String) : NowbeRequest() {
    /**
     * Attempts to notify an access from the search
     */
    fun notifyAccess() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the nickname of the visited user
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, visitantToken)
                .add(ApiUtils.KEY_NOTIFY_NICKNAME, visitantNickname)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.NOTIFY_ACCESS_FROM_SEARCH_URL
    }
}