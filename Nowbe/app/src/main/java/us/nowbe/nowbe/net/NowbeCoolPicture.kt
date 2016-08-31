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

class NowbeCoolPicture(val userToken: String, val profileToken: String, val pictureIndex: Int) : NowbeRequest() {
    /**
     * Attempts to cool a picture
     */
    fun cool() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token, profile token and picture index
        return FormBody.Builder()
        .add(ApiUtils.KEY_TOKEN, userToken)
        .add(ApiUtils.KEY_TOKEN_OWNER, profileToken)
        .add(ApiUtils.KEY_PICTURE_INDEX, pictureIndex.toString())
        .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.COOL_PICTURE_URL
    }
}