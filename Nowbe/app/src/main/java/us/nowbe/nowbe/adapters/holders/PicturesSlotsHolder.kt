package us.nowbe.nowbe.adapters.holders

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import us.nowbe.nowbe.ui.views.PicturesSlotsPictureView
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.NumberUtils

class PicturesSlotsHolder(val imageView: PicturesSlotsPictureView) : RecyclerView.ViewHolder(imageView) {
    /**
     * Binds the current image into the image view
     */
    fun bindView(picture: String, coolsCounter: Int) {
        // Display a placeholder if the data is null or the image and its cools if not
        if (picture == ApiUtils.NULL) {
            imageView.displayPlaceholder()
            imageView.hideCounter()
        } else {
            imageView.updateImage(picture)
            imageView.updateCounter(NumberUtils.roundNumber(coolsCounter))
        }
    }
}