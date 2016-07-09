package us.nowbe.nowbe.utils

import org.json.JSONObject

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ApiUtils {
    companion object {
        /**
         * Keys for the API
         */
        val KEY_USER = "user"
        val KEY_PASSWORD = "password"
        val KEY_OS = "OS"
        val KEY_NICKNAME = "nickname"
        val KEY_EMAIL = "email"

        /**
         * Non-changing values
         */
        val OS = "1"

        /**
         * Returned information from the API
         */
        val API_SUCCESS = "success"
        val API_SUCCESS_OK = "1"
        val API_SUCCESS_ERROR = "0"
        val API_SUCCESS_EXIST = "exist"
        val API_ID = "id"
        val API_TOKEN = "token"
        val API_USERNAME = "username"

        /**
         * URLs for the API requests
         */
        val BASE_URL = "https://www.nowbe.us/api/"
        val LOGIN_URL = "login.php"
        val SIGNUP_URL = "newUser.php"

        /**
         * Results of different sections of the API
         */
        enum class SignupResults {OK, NOT_OK, EXISTS}
    }
}