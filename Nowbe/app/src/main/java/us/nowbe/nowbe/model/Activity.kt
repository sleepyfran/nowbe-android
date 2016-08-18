package us.nowbe.nowbe.model

import org.json.JSONArray

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class Activity(content: MutableList<ActivityContent>) {
    /**
     * The list of ActivityContent available
     */
    val activityContent: MutableList<ActivityContent>

    init {
        this.activityContent = content
    }

    companion object {
        /**
         * Returns an Activity from a JSON array
         */
        fun fromJson(jsonArray: JSONArray): Activity {
            val content: MutableList<ActivityContent> = arrayListOf()

            // Iterate through the array getting a FeedContent from each JSON object
            for (i in 0..jsonArray.length() - 1) {
                content.add(ActivityContent.fromJson(jsonArray.getJSONObject(i)))
            }

            return Activity(content)
        }
    }
}