package us.nowbe.nowbe.net

import okhttp3.FormBody
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.model.exceptions.NoResultsException
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.utils.ApiUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeSearchUser(val type: Type, val token: String, val query: String?) : NowbeRequest() {

    companion object {
        /**
         * Types of search
         */
        enum class Type { USER, TERM, INTERESTS }
    }

    /**
     * Attempts to search for a query with the specified type and retrieve a list of results
     */
    fun search(): MutableList<SearchResult> {
        // Make the request and get the JSON data returned
        val response = super.makeRequest()

        // If the response is "null" then there're no results
        if (response == ApiUtils.NULL) throw NoResultsException()

        // Get an array from the response
        val responseArray = super.getArrayFromResponse(response)

        // Similarly if the response is empty, throw a no results exception
        if (responseArray.length() == 0) throw NoResultsException()

        // Fill a mutable list of search result from the previous array
        val results: MutableList<SearchResult> = mutableListOf()

        for (i in 0..responseArray.length() - 1) {
            results.add(SearchResult.fromJson(responseArray.getJSONObject(i)))
        }

        return results
    }

    override fun getBody(): FormBody {
        // Build the body with the token and the query
        return FormBody.Builder()
                .add(ApiUtils.KEY_TOKEN, token)
                .add(ApiUtils.KEY_SEARCH_TERM, query)
                .build()
    }

    override fun getRequestUrl(): String {
        when (type) {
            Type.USER -> return ApiUtils.SEARCH_USER_URL
            Type.INTERESTS -> return ApiUtils.SEARCH_INTERESTS_URL
            Type.TERM -> return ApiUtils.SEARCH_TERM_URL
        }
    }
}