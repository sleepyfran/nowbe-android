package us.nowbe.nowbe.ui.dialogs

import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.BottomSheetItem
import us.nowbe.nowbe.utils.IntentUtils
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SelectPictureSourceDialog : BottomSheetDialog() {

    companion object {
        /**
         * Creates a new instance of the dialog with the onDismiss implementation
         */
        fun newInstance(onTemporaryImagePath: Interfaces.OnTemporaryImagePath): BottomSheetDialog {
            val dialog = SelectPictureSourceDialog()
            dialog.onTemporaryImagePath = onTemporaryImagePath
            return dialog
        }
    }

    /**
     * Interface to be called when we have the path of the temporary image
     */
    lateinit var onTemporaryImagePath: Interfaces.OnTemporaryImagePath

    override fun onClick(item: BottomSheetItem) {
        super.onClick(item)

        // Handle the item
        if (item.drawable == R.drawable.ic_camera_black) {
            // Build the intent to open the camera
            val intentPair = IntentUtils.buildCameraIntent(context)

            // Save the path of the temporary image
            onTemporaryImagePath.onImagePath(intentPair.second)

            // Start the activity if there's an app to handle it
            if (intentPair.first.resolveActivity(activity.packageManager) != null) {
                activity.startActivityForResult(intentPair.first, IntentUtils.REQUEST_CAMERA_IMAGE)
            } else {
                // TODO: Show an error toast
            }
        } else if (item.drawable == R.drawable.ic_folder_black) {
            // Build the intent to open from a folder
            activity.startActivityForResult(IntentUtils.buildPickPhotoIntent(), IntentUtils.REQUEST_GALLERY_IMAGE)
        }
    }

    override fun getItems(): MutableList<BottomSheetItem> {
        // Initialize the items: Camera and Gallery
        return mutableListOf(
                BottomSheetItem(R.drawable.ic_camera_black, R.string.select_picture_source_camera),
                BottomSheetItem(R.drawable.ic_folder_black, R.string.select_picture_source_gallery)
        )
    }
}