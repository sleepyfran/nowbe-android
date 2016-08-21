package us.nowbe.nowbe.utils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.ViewPagerAdapter
import us.nowbe.nowbe.ui.fragments.ActivityFragment
import us.nowbe.nowbe.ui.fragments.FeedFragment
import us.nowbe.nowbe.ui.fragments.ProfileFragment
import us.nowbe.nowbe.ui.fragments.SearchTypeFragment

class TabUtils {
    companion object {
        /**
         * Method that will setup the view pager of the landing activity with the tabs
         */
        fun createLandingPagerAdapter(context: Context, fragmentManager: FragmentManager): FragmentPagerAdapter {
            val adapter = ViewPagerAdapter(fragmentManager)
            adapter.addFragment(FeedFragment(), context.getString(R.string.main_feed_tab))
            adapter.addFragment(ActivityFragment(), context.getString(R.string.main_notifications_tab))

            // Load the profile fragment with the token of the user
            adapter.addFragment(ProfileFragment.newInstance(SharedPreferencesUtils.getToken(context)!!, null),
                    context.getString(R.string.main_profile_tab))
            return adapter
        }

        /**
         * Method that will setup the view pager of the search activity with the tabs
         */
        fun createSearchPagerAdapter(context: Context, fragmentManager: FragmentManager): FragmentPagerAdapter {
            val adapter = ViewPagerAdapter(fragmentManager)
            adapter.addFragment(SearchTypeFragment(), context.getString(R.string.search_title_users))
            adapter.addFragment(SearchTypeFragment(), context.getString(R.string.search_title_terms))
            adapter.addFragment(SearchTypeFragment(), context.getString(R.string.search_title_interests))
            return adapter
        }

        /**
         * Returns the fragment from the current position of a view pager
         */
        fun getFragmentFromViewPager(fragmentManager: FragmentManager, viewPager: ViewPager, viewPagerId: Int): Fragment {
            val currentItem = viewPager.currentItem

            return fragmentManager.findFragmentByTag("android:switcher:$viewPagerId:$currentItem")
        }
    }
}
