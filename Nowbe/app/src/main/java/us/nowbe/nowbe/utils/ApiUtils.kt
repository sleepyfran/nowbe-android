package us.nowbe.nowbe.utils

import android.content.Context
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
        const val KEY_TOKEN_1 = "token1"
        const val KEY_TOKEN_2 = "token2"
        const val KEY_TOKEN_TO = "tokenTo"
        const val KEY_TOKEN_FROM = "tokenFrom"
        const val KEY_VISIBLE_NAME = "newMutableName"
        const val KEY_ABOUT = "aboutMe"

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

        // Friendship-specific results
        const val API_FRIENDSHIP_MUTUAL = "1"
        const val API_FRIENDSHIP_SEMI_MUTUAL = "0.5"
        const val API_FRIENDSHIP_NOT_FRIENDS = "0"

        // General API calls
        const val API_TIMESTAMP = "elapsedTime"

        // User-specific API calls
        const val API_USER_TOKEN = "token"
        const val API_USER_USERNAME = "username"
        const val API_USER_FULLNAME = "mutableName"
        const val API_USER_EMAIL = "email"
        const val API_USER_PROFILE_PIC = "profilePicture"
        const val API_USER_AGE = "age"
        const val API_USER_BIRTHDAY = "ageNonFormated"
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

        // Feed-specific API calls
        const val API_FEED_USERNAME = "nickname"
        const val API_FEED_UPDATE = "lastThingChanged"
        const val API_FEED_PICTURE = "pictureProfile"

        /**
         * URLs for the API requests
         */
        const val BASE_URL = "https://www.nowbe.us/api/"
        const val LOGIN_URL = "login.php"
        const val SIGNUP_URL = "newUser.php"
        const val USER_DATA_URL = "getUserDetails.php"
        const val USER_FEED_URL = "getFriends.php"
        const val USER_PICTURE_URL = "UserPictures/"
        const val USER_SLOTS_PICTURES_THUMB_URL = "slotsPicturesThumbnails/"
        const val USER_SLOTS_PICTURES_URL = "slotsPictures/"
        const val USER_ADD_FRIEND_URL = "addFriend.php"
        const val USER_REMOVE_FRIEND_URL = "removeFriend.php"
        const val USER_CHECK_IF_FRIENDS_URL = "checkMutualFriendship.php"
        const val USER_SAY_HELLO_URL = "sayHello.php"
        const val USER_UPDATE_VISIBLE_NAME_URL = "updateMutableName.php"
        const val USER_UPDATE_ABOUT_URL = "updateAboutMe.php"

        /**
         * Results of different sections of the API
         */
        enum class AddUserResults {ADDED, NOT_ADDED, NO_CONNECTION}
        enum class CheckIfFriendsResults {MUTUAL, SEMI_MUTUAL, NOT_FRIENDS}

        /**
         * Various utils
         */
        fun getFullProfilePicDir(profilePic: String): String {
            return ApiUtils.BASE_URL + ApiUtils.USER_PICTURE_URL + profilePic
        }

        fun isAppUser(context: Context, token: String): Boolean {
            return SharedPreferencesUtils.getToken(context)!! == token
        }

        fun isRelationMutual(relation: String): Boolean {
            return relation == API_FRIENDSHIP_MUTUAL
        }

        fun isRelationSemiMutual(relation: String): Boolean {
            return relation == API_FRIENDSHIP_SEMI_MUTUAL
        }

        fun hasRelation(relation: String): Boolean {
            return isRelationMutual(relation) or isRelationSemiMutual(relation)
        }
    }
}