package us.nowbe.nowbe.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_base_no_tabs.*
import kotlinx.android.synthetic.main.activity_base_tabs.*
import us.nowbe.nowbe.utils.TabUtils

class LandingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the view pager and the tab layout
        vpFragmentList.adapter = TabUtils.createPagerAdapter(this, supportFragmentManager)
        tlTabs.setupWithViewPager(vpFragmentList)

        // TODO: Provide a method to dynamically change the menu bar
    }

    override fun hasTabs(): Boolean {
        return true
    }
}