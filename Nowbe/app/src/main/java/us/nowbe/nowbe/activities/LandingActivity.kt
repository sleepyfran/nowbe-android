package us.nowbe.nowbe.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_base_toolbar.*
import us.nowbe.nowbe.utils.TabUtils

class LandingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the view pager and the tab layout
        vpFragmentList.adapter = TabUtils.createPagerAdapter(this, supportFragmentManager)
        tlTabs.setupWithViewPager(vpFragmentList)
    }

    override fun isNavigationDrawerShown(): Boolean {
        return true
    }
}