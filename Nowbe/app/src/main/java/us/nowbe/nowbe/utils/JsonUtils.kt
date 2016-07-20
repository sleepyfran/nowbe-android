package us.nowbe.nowbe.utils

import org.json.JSONException
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
        fun getNullableStringFromJson(json: JSONObject, jsonKey: String): String? {
            val string = json.optString(jsonKey)

            if (string == "null" || string == "" || string == null) {
                return null
            } else {
                return string
            }
        }

        /**
         * Returns a boolean from the JSON if it exists or null if it doesn't
         */
        fun getNullableBooleanFromJson(json: JSONObject, jsonKey: String): Boolean? {
            try {
                return json.getBoolean(jsonKey)
            } catch (e: JSONException) {
                return null
            }
        }

        /**
         * Returns an integer from the JSON if it exists or null if it doesn't
         */
        fun getNullableIntegerFromJson(json: JSONObject, jsonKey: String): Int? {
            try {
                return json.getInt(jsonKey)
            } catch (e: JSONException) {
                return null
            }
        }
    }
}