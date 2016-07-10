package us.nowbe.nowbe.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user has already logged in
        if (SharedPreferencesUtils.isLoggedIn(applicationContext)) {
            // Show'em the feed then
            startActivity(Intent(this, LandingActivity::class.java))
        } else {
            // Or the wizard instead
            startActivity(Intent(this, WizardActivity::class.java))
        }
    }
}