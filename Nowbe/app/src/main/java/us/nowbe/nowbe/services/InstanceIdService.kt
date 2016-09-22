package us.nowbe.nowbe.services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import us.nowbe.nowbe.net.async.SaveDeviceTokenObservable
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class InstanceIdService : FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token

        Log.i("TOKEN", refreshedToken)

        // Get the token of the user and save it in the server
        if (refreshedToken != null) {
            val userToken = SharedPreferencesUtils.getToken(this)

            if (userToken != null) {
                SaveDeviceTokenObservable.create(userToken, refreshedToken).subscribe(
                        {
                            // Nothing
                        },
                        {
                            // Nothing
                        }
                )
            }
        }
    }
}