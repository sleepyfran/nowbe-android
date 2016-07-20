package us.nowbe.nowbe.utils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.ViewPagerAdapter
import us.nowbe.nowbe.fragments.FeedFragment
import us.nowbe.nowbe.fragments.ProfileFragment

class TabUtils {
    companion object {
        /**
         * Method that will setup the view pager with the tabs
         */
        fun createPagerAdapter(context: Context, fragmentManager: FragmentManager): FragmentPagerAdapter {
            val adapter = ViewPagerAdapter(fragmentManager)
            adapter.addFragment(FeedFragment(), context.getString(R.string.main_feed_tab))
            adapter.addFragment(FeedFragment(), context.getString(R.string.main_notifications_tab))

            // Load the profile fragment with the token of the user
            adapter.addFragment(ProfileFragment.newInstance(SharedPreferencesUtils.getToken(context)!!),
                    context.getString(R.string.main_profile_tab))
            return adapter
        }
    }
}
