package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeRemoveComment(val token: String, val commentIndex: Int) : NowbeRequest() {
    /**
     * Attempts to remove a comment of the user
     */
    fun remove() {
        super.makeRequest()
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the index of the comment
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN_OWNER, token)
                .add(ApiUtils.KEY_SLOT_NUMBER, commentIndex.toString())
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.REMOVE_COMMENT_URL
    }
}