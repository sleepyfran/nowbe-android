package us.nowbe.nowbe.net

import okhttp3.MultipartBody
import okhttp3.Request
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

abstract class NowbeRequestUpload {
    /**
     * Attempts to upload a file to the server
     */
    fun uploadFile() {
        // Create the client to make the request
        val client = HttpClient.httpClient

        // Create the request
        val request = Request.Builder()
                .url(ApiUtils.BASE_URL + getRequestUrl())
                .post(getBody())
                .build()

        // Get the response
        val response = client.newCall(request).execute()

        // If the request was not successful throw an exception
        if (!response.isSuccessful) throw RequestNotSuccessfulException()

        // Close the response
        response.close()
    }

    /**
     * This method would be called whenever the request needs the body of the request
     */
    abstract fun getBody(): MultipartBody

    /**
     * This method would be called whenever the request needs an specific URL
     */
    abstract fun getRequestUrl(): String
}