package us.nowbe.nowbe.adapters

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import java.util.*

abstract class ViewPagerAdapter(val fragmentManager: FragmentManager) : PagerAdapter() {
    /**
     * List of fragments and their title
     */
    val fragmentList = ArrayList<Fragment?>()
    val fragmentTitleList = ArrayList<String>()

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = getItem(position)
        val trans = fragmentManager.beginTransaction()
        trans.add(container.id, fragment, "fragment:" + position)
        trans.commit()
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val trans = fragmentManager.beginTransaction()
        trans.remove(fragmentList[position])
        trans.commit()
        fragmentList[position] = null
    }

    override fun isViewFromObject(view: View, fragment: Any): Boolean {
        return (fragment as Fragment).view === view
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    abstract fun getItem(position: Int): Fragment
}
