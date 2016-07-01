package us.nowbe.nowbe.utils

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.ViewPagerAdapter
import us.nowbe.nowbe.fragments.FeedFragment

class TabUtils {
    companion object {
        /**
         * Method that will setup the view pager with the tabs
         */
        fun createPagerAdapter(context: Context, fragmentManager: FragmentManager): FragmentPagerAdapter {
            val adapter = ViewPagerAdapter(fragmentManager)
            adapter.addFragment(FeedFragment(), context.getString(R.string.main_feed_tab))
            adapter.addFragment(FeedFragment(), context.getString(R.string.main_notifications_tab))
            adapter.addFragment(FeedFragment(), context.getString(R.string.main_profile_tab))
            return adapter
        }
    }
}
