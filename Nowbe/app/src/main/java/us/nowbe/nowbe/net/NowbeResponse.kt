package us.nowbe.nowbe.net

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeResponse<T>(success: Boolean, data: T?) {
    /**
     * Boolean indicating whether the request was or not successful
     */
    var success: Boolean

    /**
     * Data to return
     */
    var data: T?

    init {
        this.success = success
        this.data = data
    }
}