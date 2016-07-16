package us.nowbe.nowbe.model

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import us.nowbe.nowbe.utils.ApiUtils
import java.util.*

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class User(token: String,
           username: String,
           fullName: String,
           status: Boolean?,
           email: String,
           profilePicDir: String,
           age: Int,
           about: String,
           friends: Int?,
           isOnline: Boolean?,
           visits: Int,
           interests: String?,
           coupleToken: String?) {

    /**
     * A Nowbe user representation
     */
    var token: String
    var username: String
    var fullName: String
    var status: Boolean?
    var email: String
    var profilePicDir: String
    var age: Int
    var about: String
    var friends: Int?
    var isOnline: Boolean?
    var visits: Int
    var interests: String?
    var coupleToken: String?
    var commentsSlots: MutableList<Slot?>
    var picturesSlots: MutableList<Slot?>

    init {
        this.token = token
        this.username = username
        this.fullName = fullName
        this.status = status
        this.email = email
        this.profilePicDir = ApiUtils.BASE_URL + ApiUtils.USER_PICTURE_URL + profilePicDir
        this.age = age
        this.about = about
        this.friends = friends
        this.isOnline = isOnline
        this.visits = visits
        this.interests = interests
        this.coupleToken = coupleToken
        this.commentsSlots = arrayListOf()
        this.picturesSlots = arrayListOf()
    }

    /**
     * Deletes all the content of the array lists
     */
    fun clearAll() {
        picturesSlots = arrayListOf()
        commentsSlots = arrayListOf()
    }

    /**
     * Adds a comment to the array of comments
     */
    fun addComment(comment: Slot) {
        commentsSlots.add(comment.index, comment)
    }

    /**
     * Adds a picture to the array of pictures
     */
    fun addPicture(picture: Slot) {
        picturesSlots.add(picture.index, picture)
    }

    companion object {

        fun fromJson(token: String, json: JSONObject): User {
            // Get the user data
            val username = json.getString(ApiUtils.API_USER_USERNAME)
            val fullname = json.getString(ApiUtils.API_USER_FULLNAME)
            val status = json.getInt(ApiUtils.API_USER_STATUS) == 1
            val email = json.getString(ApiUtils.API_USER_EMAIL)
            val profilePicDir = json.getString(ApiUtils.API_USER_PROFILE_PIC)
            val age = json.getInt(ApiUtils.API_USER_AGE)
            val about = json.getString(ApiUtils.API_USER_ABOUT)
            val friends = json.getInt(ApiUtils.API_USER_FRIENDS)
            var isOnline: Boolean? = null

            // Try to obtain the online state (might not be available)
            try {
                isOnline = json.getString(ApiUtils.API_USER_IS_ONLINE) == "1"
            } catch (e: JSONException) {
                Log.e("NowbeUserData", "Cannot find isOnline string")
            }

            val visits = json.getInt(ApiUtils.API_USER_VISITS)
            val interest = json.getString(ApiUtils.API_USER_INTERESTS)
            val coupleToken = json.getString(ApiUtils.API_USER_COUPLE_TOKEN)

            // Make the user from this data TODO: Check the isOnline
            val user = User(token,
                    username,
                    fullname,
                    status,
                    email,
                    profilePicDir,
                    age,
                    about,
                    friends,
                    isOnline,
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

            return user
        }
    }
}