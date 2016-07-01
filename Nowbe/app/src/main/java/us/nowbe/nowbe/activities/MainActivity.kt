package us.nowbe.nowbe.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.utils.TabUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the activity and the toolbar
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Setup the view pager and the tab layout
        vpFragmentList.adapter = TabUtils.createPagerAdapter(this, supportFragmentManager)
        tlTabs.setupWithViewPager(vpFragmentList)

        searchFab.setOnClickListener(View.OnClickListener {
            view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        })
    }
}
