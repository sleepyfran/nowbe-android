package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeReplyComment(val token: String, val tokenOwner: String, val reply: String, val commentNo: Int) : NowbeRequest() {
    /**
     * Attempts to reply to a comment
     */
    fun reply() {
        // Make the request
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token, reply, token owner and the number of the comment
        // TODO: Check the hell out of this
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_REPLY, reply)
                .add(ApiUtils.KEY_TOKEN_OWNER, tokenOwner)
                .add(ApiUtils.KEY_COMMENT_INDEX, commentNo.toString())
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.REPLY_COMMENT_URL
    }
}