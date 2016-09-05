package us.nowbe.nowbe.net

import okhttp3.FormBody
import org.json.JSONArray
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeGetCoolers(val token: String, val imgId: String) : NowbeRequest() {
    /**
     * Attempts to returns the cools of an image
     */
    fun getCoolers(): JSONArray {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()
        return super.getArrayFromResponse(response)
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the ID of the image
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_IMG_ID, imgId)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.GET_COOLERS_URL
    }
}