package us.nowbe.nowbe.model

import org.json.JSONObject
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ActivityContent(activity: String,
                      tokenWhoMadeActivity: String,
                      date: String,
                      id: String) {
    /**
     * A Nowbe activity representation
     */
    var activity: String
    var tokenWhoMadeActivity: String
    var pictureDirWhoMadeActivity: String
    var date: String
    var id: String

    init {
        this.activity = activity
        this.tokenWhoMadeActivity = tokenWhoMadeActivity
        this.pictureDirWhoMadeActivity = ApiUtils.getThumbProfilePicDir(tokenWhoMadeActivity) + ".jpg"
        this.date = date
        this.id = id
    }

    companion object {
        /**
         * Returns a FeedContent from a JSON object
         */
        fun fromJson(jsonObject: JSONObject): ActivityContent {
            // Get the info from the provided JSON object and return a FeedContent object from it
            val activity = jsonObject.getString(ApiUtils.KEY_ACTIVITY_REASON)
            val tokenWhoMadeActivity = jsonObject.getString(ApiUtils.KEY_ACTIVITY_TOKEN)
            val date = jsonObject.getString(ApiUtils.KEY_ACTIVITY_DATE)
            val id = jsonObject.getString(ApiUtils.KEY_ACTIVITY_ID)

            return ActivityContent(activity, tokenWhoMadeActivity, date, id)
        }
    }
}