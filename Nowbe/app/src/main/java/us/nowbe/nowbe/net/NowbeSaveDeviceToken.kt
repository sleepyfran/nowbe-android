package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeSaveDeviceToken(val userToken: String, val deviceToken: String) : NowbeRequest() {
    /**
     * Attempts to save the device token of the user
     */
    fun save() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the user token and the device token
        return FormBody.Builder()
                .add(ApiUtils.KEY_USER_TOKEN, userToken)
                .add(ApiUtils.KEY_DEVICE_TOKEN, deviceToken)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.SAVE_TOKEN_DEVICE_URL
    }
}