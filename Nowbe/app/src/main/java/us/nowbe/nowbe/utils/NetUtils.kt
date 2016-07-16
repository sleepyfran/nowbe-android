package us.nowbe.nowbe.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class NetUtils {
    companion object {
        /**
         * Checks whether there's an available connection active
         */
        fun isConnectionAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo == null || !networkInfo.isConnected) {
                return false
            }

            return true
        }
    }
}