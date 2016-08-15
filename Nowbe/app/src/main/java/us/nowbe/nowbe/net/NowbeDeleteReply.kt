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

class NowbeDeleteReply(val tokenOwner: String, val reply: CommentReply) : NowbeRequest() {
    /**
     * Attempts to remove the reply of an user
     */
    fun remove() {
        // Make the request and get the JSON data returned
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with id, token and reply token
        return FormBody.Builder()
                .add(ApiUtils.KEY_REPLY_ID_REQUEST, reply.id)
                .add(ApiUtils.KEY_OWNER_TOKEN, tokenOwner)
                .add(ApiUtils.KEY_USER_TOKEN_REPLY, reply.token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.COMMENT_DELETE_REPLY_URL
    }
}