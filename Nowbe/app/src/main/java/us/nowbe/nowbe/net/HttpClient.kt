package us.nowbe.nowbe.net

import okhttp3.OkHttpClient

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class HttpClient {
    companion object {
        // Unique instance of the OkHttpClient
        val httpClient = OkHttpClient()
    }
}