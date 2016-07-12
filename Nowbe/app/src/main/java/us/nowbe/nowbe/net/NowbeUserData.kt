package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.Slot
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeUserData(val token: String) : NowbeRequest() {

    /**
     * Attempts to get an user by its token
     */
    fun getUser(): User? {
        // Make the request and get the JSON data returned
        val json = super.getFirstObjectFromArray()
        val success = json.length() != 0
        var user: User? = null

        if (success) {
            // Get the user data
            val username = json.getString(ApiUtils.API_USER_USERNAME)
            val fullname = json.getString(ApiUtils.API_USER_FULLNAME)
            val status = json.getInt(ApiUtils.API_USER_STATUS) == 1
            val email = json.getString(ApiUtils.API_USER_EMAIL)
            val profilePicDir = json.getString(ApiUtils.API_USER_PROFILE_PIC)
            val age = json.getInt(ApiUtils.API_USER_AGE)
            val about = json.getString(ApiUtils.API_USER_ABOUT)
            val friends = json.getInt(ApiUtils.API_USER_FRIENDS)
            //val isOnline = json.getString(ApiUtils.API_USER_IS_ONLINE) == "1"
            val visits = json.getInt(ApiUtils.API_USER_VISITS)
            val interest = json.getString(ApiUtils.API_USER_INTERESTS)
            val coupleToken = json.getString(ApiUtils.API_USER_COUPLE_TOKEN)

            // Make the user from this data TODO: Check the isOnline
            user = User(token,
                    username,
                    fullname,
                    status,
                    email,
                    profilePicDir,
                    age,
                    about,
                    friends,
                    false,
                    visits,
                    interest,
                    coupleToken)

            // Add the pictures and comments
            for (i in 0..4) {
                val picture = json.getString(ApiUtils.API_USER_PICTURE_SLOT + i)
                val pictureCools = json.getInt(ApiUtils.API_USER_PICTURE_SLOT_COOLS + i)
                val comment = json.getString(ApiUtils.API_USER_COMMENT_SLOT + i)

                user.addPicture(Slot(picture, i, pictureCools))
                user.addComment(Slot(comment, i, null))
            }
        }

        return user
    }

    override fun getBody(): FormBody {
        // Build the body with the token
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_DATA_URL
    }
}