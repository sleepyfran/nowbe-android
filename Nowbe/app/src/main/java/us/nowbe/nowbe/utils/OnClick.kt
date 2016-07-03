package us.nowbe.nowbe.utils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class OnClick {
    interface OnPictureSlotClick {
        /**
         * (Should) return the position of the item clicked so the adapter can show the photo
         */
        fun onPictureSlotClick(itemSelected: Int)
    }
}