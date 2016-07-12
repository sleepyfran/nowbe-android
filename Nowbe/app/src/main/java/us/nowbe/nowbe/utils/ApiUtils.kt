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
        val KEY_TOKEN = "token"

        /**
         * Non-changing values
         */
        val OS = "2"

        /**
         * Returned information from the API
         */
        val API_SUCCESS = "success"
        val API_SUCCESS_OK = "1"
        val API_SUCCESS_ERROR = "0"
        val API_SUCCESS_EXIST = "exist"
        val API_ID = "id"

        // User-specific API calls
        val API_USER_TOKEN = "token"
        val API_USER_USERNAME = "username"
        val API_USER_FULLNAME = "mutableName"
        val API_USER_EMAIL = "email"
        val API_USER_PROFILE_PIC = "profilePicture"
        val API_USER_AGE = "age"
        val API_USER_ABOUT = "aboutUser"
        val API_USER_FRIENDS = "numberOfFriends"
        val API_USER_VISITS = "visits"
        val API_USER_INTERESTS = "interests"
        val API_USER_STATUS = "status"
        val API_USER_IS_ONLINE = "isOnline"
        val API_USER_COUPLE_TOKEN = "coupleToken"
        val API_USER_PICTURE_SLOT = "picture"
        val API_USER_PICTURE_SLOT_COOLS = "coolSlot"
        val API_USER_COMMENT_SLOT = "comment"

        /**
         * URLs for the API requests
         */
        val BASE_URL = "https://www.nowbe.us/api/"
        val LOGIN_URL = "login.php"
        val SIGNUP_URL = "newUser.php"
        val USER_DATA_URL = "getUserDetails.php"
        val USER_PICTURE_URL = "UserPictures/"
        val USER_SLOTS_PICTURES_THUMB_URL = "slotsPicturesThumbnails/"
        val USER_SLOTS_PICTURES_URL = "slotsPictures/"

        /**
         * Results of different sections of the API
         */
        enum class SignupResults {OK, NOT_OK, EXISTS}
    }
}