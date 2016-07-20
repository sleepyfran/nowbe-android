package us.nowbe.nowbe.model

import org.json.JSONArray

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class Feed(feedContent: MutableList<FeedContent>) {
    /**
     * The list of FeedContent available
     */
    val feedContent: MutableList<FeedContent>

    init {
        this.feedContent = feedContent
    }

    companion object {
        /**
         * Returns a Feed from a JSON array
         */
        fun fromJson(jsonArray: JSONArray): Feed {
            val content: MutableList<FeedContent> = arrayListOf()

            // Iterate through the array getting a FeedContent from each JSON object
            for (i in 0..jsonArray.length() - 1) {
                content.add(FeedContent.fromJson(jsonArray.getJSONObject(i)))
            }

            return Feed(content)
        }
    }
}