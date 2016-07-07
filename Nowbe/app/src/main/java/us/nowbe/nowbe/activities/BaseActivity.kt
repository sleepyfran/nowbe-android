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
abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup the activity and the toolbar
        if (hasTabs()) {
            setContentView(R.layout.activity_base_tabs)
        } else {
            setContentView(R.layout.activity_base_no_tabs)
        }

        setSupportActionBar(toolbar)

        // Hide the navigation drawer if we don't want it to be displayed
        if (!isNavigationDrawerShown()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            // Setup the navigation view
            val navigationToggle = ActionBarDrawerToggle(this,
                    drawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close)
            drawerLayout.addDrawerListener(navigationToggle)
            navigationToggle.syncState()

            // Set this class as the navigation item selected
            navigationView.setNavigationItemSelectedListener(this)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem?): Boolean {
        // TODO: Setup the navigation drawer
        return true
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
     * This method will be called whenever the activity wants to check if there's a navigation drawer
     * available. If true, the activity will show it or hide it if not
     */
    abstract fun isNavigationDrawerShown(): Boolean

    /**
     * This method will be called whenever the activity has to inflate the content so it'll select
     * it based on this
     */
    abstract fun hasTabs(): Boolean
}
