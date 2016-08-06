package us.nowbe.nowbe.utils

import us.nowbe.nowbe.model.BottomSheetItem
import us.nowbe.nowbe.model.User

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class Interfaces {
    /**
     * Interface to implement when a picture slot is clicked
     */
    interface OnPictureSlotClick {
        /**
         * (Should) return the position of the item clicked so the adapter can show the photo
         */
        fun onPictureSlotClick(itemSelected: Int)
    }

    interface OnCommentSlotClick {
        /**
         * (Should) return the position of the item clicked so the adapter can do whatever it wants with the comment
         */
        fun onCommentSlotClick(itemSelected: Int)
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

    /**
     * Interface to implement when the profile fragment (or similar) needs to return an user
     */
    interface OnUserResult {
        /**
         * (Should) return the user that we got from the API call
         */
        fun onUserResult(user: User)
    }

    /**
     * Interface to implement when the user pressed and item on a Bottom Sheet
     */
    interface OnBottomSheetItemClick {
        /**
         * (Should) return the item clicked
         */
        fun onClick(item: BottomSheetItem)
    }

    /**
     * Interface to call when we have a path for a temporary image
     */
    interface OnTemporaryImagePath {
        /**
         * (Should) pass the path of the temporary image
         */
        fun onImagePath(imagePath: String)
    }
}