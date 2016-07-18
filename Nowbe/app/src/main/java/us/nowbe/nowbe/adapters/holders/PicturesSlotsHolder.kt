package us.nowbe.nowbe.adapters.holders

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v7.widget.RecyclerView
import android.view.View
import us.nowbe.nowbe.utils.NumberUtils
import us.nowbe.nowbe.utils.OnClick
import us.nowbe.nowbe.views.PicturesSlotsPictureView

class PicturesSlotsHolder(val imageView: PicturesSlotsPictureView) : RecyclerView.ViewHolder(imageView) {
    /**
     * Binds the current image into the image view
     */
    fun bindView(picture: String, coolsCounter: Int, onClick: OnClick.OnPictureSlotClick, position: Int) {
        imageView.updateImage(picture)
        imageView.updateCounter(NumberUtils.roundNumber(coolsCounter))

        // Notify about the pressed picture to the adapter
        imageView.setOnClickListener({ onClick.onPictureSlotClick(position) })
    }
}