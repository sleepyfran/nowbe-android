package us.nowbe.nowbe.net

import android.util.Log
import okhttp3.FormBody
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
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
    fun makeRequest(): String {
        // Create the client to make the request
        val client = HttpClient.httpClient

        // Create the request
        val request = Request.Builder()
                .url(ApiUtils.BASE_URL + getRequestUrl())
                .post(getBody())
                .build()

        // Get the response
        val response = client.newCall(request).execute().body().string()

        // Log the response
        Log.i(javaClass.simpleName, response)

        // Return the response
        return response
    }

    /**
     * Makes a JSON object out of a JSON string
     */
    fun getObjectFromResponse(): JSONObject {
        return JSONObject(makeRequest())
    }

    /**
     * Makes a JSON object out of the first JSON object on the response array
     */
    fun getFirstObjectFromArray(): JSONObject {
        return JSONArray(makeRequest()).getJSONObject(0)
    }

    /**
     * Makes a JSON array out of a JSON string
     */
    fun getArrayFromResponse(): JSONArray {
        return JSONArray(makeRequest())
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