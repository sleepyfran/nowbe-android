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
        const val KEY_USER = "user"
        const val KEY_PASSWORD = "password"
        const val KEY_OS = "OS"
        const val KEY_NICKNAME = "nickname"
        const val KEY_EMAIL = "email"
        const val KEY_TOKEN = "token"

        /**
         * Non-changing values
         */
        const val NULL = "null"
        const val OS = "2"

        /**
         * Returned information from the API
         */
        const val API_SUCCESS = "success"
        const val API_SUCCESS_OK = "1"
        const val API_SUCCESS_ERROR = "0"
        const val API_SUCCESS_EXIST = "exist"
        const val API_ID = "id"

        // User-specific API calls
        const val API_USER_TOKEN = "token"
        const val API_USER_USERNAME = "username"
        const val API_USER_FULLNAME = "mutableName"
        const val API_USER_EMAIL = "email"
        const val API_USER_PROFILE_PIC = "profilePicture"
        const val API_USER_AGE = "age"
        const val API_USER_ABOUT = "aboutUser"
        const val API_USER_FRIENDS = "numberOfFriends"
        const val API_USER_VISITS = "visits"
        const val API_USER_INTERESTS = "interests"
        const val API_USER_EDUCATION = "studies"
        const val API_USER_STATUS = "status"
        const val API_USER_IS_ONLINE = "isOnline"
        const val API_USER_COUPLE_TOKEN = "coupleToken"
        const val API_USER_COUPLE_NAME = "coupleMutableName"
        const val API_USER_PICTURE_SLOT = "picture"
        const val API_USER_PICTURE_SLOT_COOLS = "coolSlot"
        const val API_USER_COMMENT_SLOT = "comment"

        /**
         * URLs for the API requests
         */
        const val BASE_URL = "https://www.nowbe.us/api/"
        const val LOGIN_URL = "login.php"
        const val SIGNUP_URL = "newUser.php"
        const val USER_DATA_URL = "getUserDetails.php"
        const val USER_PICTURE_URL = "UserPictures/"
        const val USER_SLOTS_PICTURES_THUMB_URL = "slotsPicturesThumbnails/"
        const val USER_SLOTS_PICTURES_URL = "slotsPictures/"

        /**
         * Results of different sections of the API
         */
        enum class RequestResults {OK, NO_CONNECTION}
        enum class SignupResults {OK, NOT_OK, EXISTS, NO_CONNECTION}
        enum class LoginResults {LOGGED, WRONG_DATA, NO_CONNECTION}
    }
}