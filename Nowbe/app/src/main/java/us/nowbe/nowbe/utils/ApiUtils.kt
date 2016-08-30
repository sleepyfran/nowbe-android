package us.nowbe.nowbe.utils

import android.content.Context

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
        const val KEY_TOKEN_WANT_TO_SEE = "tokenWantToSee"
        const val KEY_TOKEN_1 = "token1"
        const val KEY_TOKEN_2 = "token2"
        const val KEY_TOKEN_OWNER = "tokenOwner"
        const val KEY_OWNER_TOKEN = "ownerToken"
        const val KEY_TOKEN_COMMENT_OWNER = "tokenCommentOwner"
        const val KEY_PUBLIC = "public"
        const val KEY_USER_TOKEN = "userToken"
        const val KEY_REPLY_TOKEN = "replyUserToken"
        const val KEY_REPLY_ID = "id"
        const val KEY_FAKE_TOKEN = "fakeToken"
        const val KEY_TOKEN_TO = "tokenTo"
        const val KEY_TOKEN_FROM = "tokenFrom"
        const val KEY_VISIBLE_NAME = "newMutableName"
        const val KEY_ABOUT = "aboutMe"
        const val KEY_AGE = "age"
        const val KEY_INTERESTS = "interests"
        const val KEY_EDUCATION = "studies"
        const val KEY_COUPLE = "couple"
        const val KEY_COMMENT_INDEX = "commentNo"
        const val KEY_COMMENT_DATA = "comment"
        const val KEY_REPLY = "reply"
        const val KEY_TIMESTAMP = "timestamp"
        const val KEY_REPLY_ID_REQUEST = "idReply"
        const val KEY_USER_TOKEN_REPLY = "userTokenReply"
        const val KEY_ACTIVITY_REASON = "reason"
        const val KEY_ACTIVITY_TOKEN = "token2"
        const val KEY_ACTIVITY_DATE = "date"
        const val KEY_ACTIVITY_ID = "id"
        const val KEY_SEARCH_TERM = "searchTerm"
        const val KEY_SEARCH_ABOUT = "about"
        const val KEY_SEARCH_MUTUAL = "isMutual"
        const val KEY_NOTIFY_NICKNAME = "userVisitant"
        const val KEY_STATUS = "status"
        const val KEY_IMG_ID = "imgId"

        /**
         * Non-changing values
         */
        const val NULL = "null"
        const val EMPTY = ""
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
        const val API_USER_PROFILE_PIC_ALT = "pictureProfile"
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
        const val USER_PICTURE_THUMB_URL = "UserPicturesThumbnails/"
        const val USER_SLOTS_PICTURES_THUMB_URL = "slotsPicturesThumbnails/"
        const val USER_SLOTS_PICTURES_URL = "slotsPictures/"
        const val USER_ADD_FRIEND_URL = "addFriend.php"
        const val USER_REMOVE_FRIEND_URL = "removeFriend.php"
        const val USER_CHECK_IF_FRIENDS_URL = "checkMutualFriendship.php"
        const val USER_SAY_HELLO_URL = "sayHello.php"
        const val USER_UPDATE_VISIBLE_NAME_URL = "updateMutableName.php"
        const val USER_UPDATE_ABOUT_URL = "updateAboutMe.php"
        const val USER_UPDATE_BIRTHDAY_URL = "updateAge.php"
        const val USER_UPDATE_INTERESTS_URL = "updateInterests.php"
        const val USER_UPDATE_EDUCATION_URL = "updateStudies.php"
        const val USER_UPDATE_COUPLE_URL = "updateCouple.php"
        const val USER_UPDATE_AVATAR_URL = "uploadImageProfile.php"
        const val USER_UPDATE_SLOT_PICTURE_URL = "uploadPicturesSlots.php"
        const val USER_UPDATE_COMMENT_URL = "updateComments.php"
        const val REPLY_COMMENT_URL = "sendCommentReply.php"
        const val COMMENT_REPLIES_URL = "getRepliesComments.php"
        const val COMMENT_DELETE_REPLY_URL = "removeReply.php"
        const val ACTIVITY_DATA_URL = "getUserActivity.php"
        const val ACTIVITY_DELETE_ONE_URL = "removeActivity.php"
        const val ACTIVITY_DELETE_ALL_URL = "clearAllNotifications.php"
        const val SEARCH_INTERESTS_URL = "searchInterests.php"
        const val SEARCH_TERM_URL = "searchTerms.php"
        const val SEARCH_USER_URL = "searchUsers.php"
        const val NOTIFY_ACCESS_FROM_SEARCH_URL = "notificationUserAccessedFromSearch.php"
        const val CHANGE_STATUS_URL = "updateStatus.php"
        const val USER_REMOVE_COUPLE_URL = "removeCouple.php"
        const val GET_COOLS_URL = "getCooler.php"

        /**
         * POST parameters
         */
        const val USER_UPDATE_PROFILE_NAME_PARAM = "name"
        const val USER_UPDATE_PROFILE_PIC_PARAM = "userPicture"
        const val USER_UPDATE_SLOT_PIC_PARAM = "slots"

        /**
         * Various utils
         */
        fun getFullProfilePicDir(profilePic: String): String {
            return BASE_URL + USER_PICTURE_URL + profilePic
        }

        fun getThumbProfilePicDir(profilePic: String): String {
            return BASE_URL + USER_PICTURE_THUMB_URL + profilePic
        }

        fun getFullSlotPicDir(slotPic: String): String {
            return ApiUtils.BASE_URL + ApiUtils.USER_SLOTS_PICTURES_URL + slotPic
        }

        fun getThumbSlotPicDir(slotPic: String): String {
            return ApiUtils.BASE_URL + ApiUtils.USER_SLOTS_PICTURES_THUMB_URL + slotPic
        }

        fun getUpdateSlotName(token: String, slotIndex: Int): String {
            return token + slotIndex.toString() + ".jpg"
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