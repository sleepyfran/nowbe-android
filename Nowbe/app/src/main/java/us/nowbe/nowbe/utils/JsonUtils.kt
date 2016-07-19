package us.nowbe.nowbe.utils

import org.json.JSONObject

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class JsonUtils {
    companion object {
        /**
         * Returns a string from the JSON if it exists or null if it is empty, doesn't exists or it contains "null"
         */
        fun getNullableStringFromJson(json: JSONObject, apiKey: String): String? {
            val string = json.getString(apiKey)

            if (string == "null" || string == "" || string == null) {
                return null
            } else {
                return string
            }
        }
    }
}