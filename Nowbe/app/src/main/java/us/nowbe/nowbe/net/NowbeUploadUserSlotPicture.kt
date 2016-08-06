package us.nowbe.nowbe.net

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import us.nowbe.nowbe.utils.ApiUtils
import java.io.File

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NowbeUploadUserSlotPicture(val token: String, val file: File, val slotIndex: Int) : NowbeRequestUpload() {
    /**
     * Attempts to upload a slot picture
     */
    fun upload() {
        super.uploadFile()
    }

    override fun getBody(): MultipartBody {
        // Build the body with the token and the picture to upload
        return MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(ApiUtils.USER_UPDATE_SLOT_PIC_PARAM,
                        ApiUtils.getUpdateSlotName(token, slotIndex),
                        RequestBody.create(MediaType.parse("image/jpeg"), file))
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_SLOT_PICTURE_URL
    }
}