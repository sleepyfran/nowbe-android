package us.nowbe.nowbe.net

import android.util.Log
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

abstract class NowbeRequest() {
    /**
     * Makes a request delegating to subclasses the body and URL of it
     */
    fun makeRequest(): JSONObject {
        // Create the client to make the request
        val client = OkHttpClient()

        // Create the request
        val request = Request.Builder()
                .url(ApiUtils.BASE_URL + getRequestUrl())
                .post(getBody())
                .build()

        // Get the response
        val response = client.newCall(request).execute().body().string()

        // Log the response
        Log.i(javaClass.simpleName, response)

        // Return it as a JSON string
        return JSONObject(response)
    }

    /**
     * This method would be called whenever the request needs the body of it
     */
    abstract fun getBody(): FormBody

    /**
     * This method would be called whenever the request needs an specific URL
     */
    abstract fun getRequestUrl(): String
}