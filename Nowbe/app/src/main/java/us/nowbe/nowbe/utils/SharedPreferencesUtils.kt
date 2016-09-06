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
         * Username of the user in Nowbe
         */
        val USERNAME_KEY = "username"

        /**
         * Profile visibility of the user in Nowbe
         */
        val PROFILE_VISIBILITY_KEY = "profileVisibility"

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

            sharedPrefs.edit().putString(TOKEN_KEY, token).apply()
        }

        /**
         * Returns the username of the user if it exists
         */
        fun getUsername(context: Context): String? {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            if (sharedPrefs.contains(USERNAME_KEY)) {
                return sharedPrefs.getString(USERNAME_KEY, "None")
            } else {
                return null
            }
        }

        /**
         * Sets the username of the user in Nowbe
         */
        fun setUsername(context: Context, username: String) {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            sharedPrefs.edit().putString(USERNAME_KEY, username).apply()
        }

        /**
         * Returns the profile visilibility of the user if it exists
         */
        fun getProfileVisibility(context: Context): Boolean? {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            if (sharedPrefs.contains(PROFILE_VISIBILITY_KEY)) {
                return sharedPrefs.getBoolean(PROFILE_VISIBILITY_KEY, false)
            } else {
                return null
            }
        }

        /**
         * Sets the profile visibility in Nowbe
         */
        fun setProfileVisibility(context: Context, visibility: Boolean) {
            val sharedPrefs = context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)

            sharedPrefs.edit().putBoolean(PROFILE_VISIBILITY_KEY, visibility).apply()
        }

        /**
         * Clears the whole data stored in the shared preferences
         */
        fun clearData(context: Context) {
            context.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE).edit().clear().apply()
        }
    }
}