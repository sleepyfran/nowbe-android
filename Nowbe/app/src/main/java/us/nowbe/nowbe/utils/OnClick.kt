package us.nowbe.nowbe.utils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class OnClick {
    /**
     * Interface to implement when a picture slot is clicked
     */
    interface OnPictureSlotClick {
        /**
         * (Should) return the position of the item clicked so the adapter can show the photo
         */
        fun onPictureSlotClick(itemSelected: Int)
    }

    /**
     * Interface to implement when a feed item is clicked
     */
    interface OnFeedItemClick {
        /**
         * (Should) return the position of the item clicked so the adapter can react to it
         */
        fun onFeedItemClick(itemSelected: Int)
    }
}