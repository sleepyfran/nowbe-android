package us.nowbe.nowbe.net

import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class HttpClient {
    companion object {
        // Cache size, 10MiB
        val cacheSize: Long = 10 * 1024 * 1024

        // Unique instance of the OkHttpClient
        lateinit var httpClient: OkHttpClient

        fun configureClient(file: File) {
            val cache = Cache(file, cacheSize)

            httpClient = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .cache(cache)
                    .build()
        }
    }
}