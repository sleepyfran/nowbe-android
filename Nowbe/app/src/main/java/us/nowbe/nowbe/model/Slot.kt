package us.nowbe.nowbe.model

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class Slot(data: String, index: Int, cools: Int?, hasCooled: Boolean? = null) {
    /**
     * A slot representation
     */
    var data: String
    var index: Int
    var cools: Int?
    var hasCooled: Boolean?

    init {
        this.data = data
        this.index = index
        this.cools = cools
        this.hasCooled = hasCooled
    }
}