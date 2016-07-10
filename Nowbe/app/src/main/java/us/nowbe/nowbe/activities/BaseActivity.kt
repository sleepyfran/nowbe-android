package us.nowbe.nowbe.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_base_no_tabs.*
import kotlinx.android.synthetic.main.navigation_drawer.*
import us.nowbe.nowbe.R

/**
 * Defines a base activity used by any other activity of the app
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the activity and the toolbar
        if (hasTabs()) {
            setContentView(R.layout.activity_base_tabs)
        } else {
            setContentView(R.layout.activity_base_no_tabs)
        }

        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu of the base activity
        menuInflater.inflate(R.menu.menu_base_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val selectedId = item?.itemId

        if (selectedId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * This method will be called whenever the activity has to inflate the content so it'll select
     * it based on this
     */
    abstract fun hasTabs(): Boolean
}
