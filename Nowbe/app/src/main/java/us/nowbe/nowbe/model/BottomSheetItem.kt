package us.nowbe.nowbe.model

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class BottomSheetItem(drawable: Int, text: Int) {
    /**
     * Drawable of the action
     */
    var drawable: Int

    /**
     * Text of the action
     */
    var text: Int

    init {
        this.drawable = drawable
        this.text = text
    }
}