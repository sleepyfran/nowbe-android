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

class NowbeUploadUserAvatar(val token: String, val file: File) : NowbeRequestUpload() {

    /**
     * Upload the specified file
     */
    fun upload() {
        super.uploadFile()
    }

    override fun getBody(): MultipartBody {
        // Build the body with the token and the picture to upload
        return MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(ApiUtils.USER_UPDATE_PROFILE_PIC_PARAM, token + ".jpg", RequestBody.create(MediaType.parse("image/jpeg"), file))
                .addFormDataPart(ApiUtils.USER_UPDATE_PROFILE_NAME_PARAM, token)
                .build()
    }

    override fun getRequestUrl(): String {
        return ApiUtils.USER_UPDATE_AVATAR_URL
    }
}