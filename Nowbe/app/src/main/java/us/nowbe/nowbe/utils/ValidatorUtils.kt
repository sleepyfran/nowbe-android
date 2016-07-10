package us.nowbe.nowbe.utils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ValidatorUtils {
    companion object {
        /**
         * Returns whether an email is or not valid
         */
        fun isEmailValid(email: String): Boolean {
            if (email.isEmpty()) return false
            else return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}