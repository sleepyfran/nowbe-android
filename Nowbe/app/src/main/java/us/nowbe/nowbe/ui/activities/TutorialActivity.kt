package us.nowbe.nowbe.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tutorial.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.utils.TabUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        // Create the view pager with the tutorial
        val pagerAdapter = TabUtils.createTutorialPagerAdapter(supportFragmentManager)
        vpTutorialList.adapter = pagerAdapter
    }
}