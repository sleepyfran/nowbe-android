package us.nowbe.nowbe.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.os.Bundle
import us.nowbe.nowbe.R
import us.nowbe.nowbe.fragments.ProfileFragment
import us.nowbe.nowbe.utils.ApiUtils

class ProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra(ApiUtils.API_USER_TOKEN)

        // Load the profile fragment with the specified token
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment, ProfileFragment.newInstance(token))
                .commit()
    }

    override fun hasTabs(): Boolean {
        return false
    }
}