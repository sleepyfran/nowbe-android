package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.CommentReply
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeCommentReplies(val token: String, val tokenOwner: String, val commentNo: Int) : NowbeRequest() {
    /**
     * Attempts to get the replies of a comment
     */
    fun getReplies(): MutableList<CommentReply> {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()

        // Get the object from the response and check whether it was or not successful
        val json = super.getArrayFromResponse(response)

        // Make a mutable list out of the JSON array
        val replies: MutableList<CommentReply> = mutableListOf()

        for (i in 0..json.length() - 1) {
            replies.add(CommentReply.fromJson(json.getJSONObject(i)))
        }

        return replies
    }

    override fun getBody(): FormBody {
        // Build the body with the token, the token owner and the comment number
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_TOKEN_OWNER, tokenOwner)
                .add(ApiUtils.KEY_COMMENT_INDEX, commentNo.toString())
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.COMMENT_REPLIES_URL
    }
}