package us.nowbe.nowbe.utils

import android.content.Context

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SharedPreferencesUtils {
    companion object {
        /**
         * The key of the shared preferences of the application
         */
        val SHARED_PREFERENCES_KEY = "us.nowbe.nowbe"

        /**
         * List of keys that we'll be using
         */
        val LOGGED_IN_KEY = "loggedIn"

        /**
         * Token of the user in Nowbe
         */
        val TOKEN_KEY = "token"

        /**
         * Checks whether we have prove that the user has or not logged into Nowbe
         */
        fun isLoggedIn(context: Context): Boolean {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            return sharedPrefs.contains(LOGGED_IN_KEY)
        }

        /**
         * Sets whether the use has or not logged into Nowbe
         */
        fun setLoggedIn(context: Context, loggedIn: Boolean) {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            sharedPrefs.edit().putBoolean(LOGGED_IN_KEY, loggedIn).apply()
        }

        /**
         * Returns the token of the user if it exists
         */
        fun getToken(context: Context): String? {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            if (sharedPrefs.contains(TOKEN_KEY)) {
                return sharedPrefs.getString(TOKEN_KEY, "None")
            } else {
                return null
            }
        }

        /**
         * Sets the token of the user in Nowbe
         */
        fun setToken(context: Context, token: String) {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            sharedPrefs.edit().putString(TOKEN_KEY, token)
        }
    }
}