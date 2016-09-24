package us.nowbe.nowbe.ui.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_base_tabs.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.ViewPagerAdapter
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.net.async.ChangeStatusObservable
import us.nowbe.nowbe.net.async.SaveDeviceTokenObservable
import us.nowbe.nowbe.net.async.UpdateOnlineObservable
import us.nowbe.nowbe.ui.dialogs.ChangeAvailabilityDialog
import us.nowbe.nowbe.ui.fragments.ActivityFragment
import us.nowbe.nowbe.ui.fragments.ProfileFragment
import us.nowbe.nowbe.utils.*

class LandingActivity : BaseActivity() {

    companion object {
        /**
         * Request codes
         */
        const val REQUEST_EDIT = 1
    }

    /**
     * Adapter of the view pager
     */
    lateinit var pagerAdapter: ViewPagerAdapter

    /**
     * Menu of the activity
     */
    var menu: Menu? = null

    /**
     * Setups the fab with the search icon and action
     */
    fun setupSearchFab() {
        // Show the search icon in the fab
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search_white))

        // Show the Search Activity when clicking the fab
        fab.setOnClickListener {
            // Create the intent passing the fab position
            val searchIntent = Intent(this@LandingActivity, SearchActivity::class.java)
            searchIntent.putExtra(IntentUtils.SEARCH_RESULT, SearchActivity.Companion.SearchResultClick.OPEN_PROFILE)
            searchIntent.putExtra(IntentUtils.FAB_X_POSITION, fab.right)
            searchIntent.putExtra(IntentUtils.FAB_Y_POSITION, fab.bottom)
            startActivity(searchIntent)

            // Don't show animations, we'll handle that
            overridePendingTransition(0, 0)
        }
    }

    /**
     * Setups the fab with the delete all icon and action
     */
    fun setupNotificationsFab() {
        // Show the clear icon in the fab
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_clear_white))

        // Clear all the notifications
        fab.setOnClickListener {
            // Get the adapter
            val fragment = TabUtils.getFragmentFromViewPager(supportFragmentManager,
                    vpFragmentList,
                    R.id.vpFragmentList) as ActivityFragment

            // Remove all the notifications
            fragment.removeAllActivity()
        }
    }

    /**
     * Setups the fab with the edit icon and action
     */
    fun setupEditFab() {
        // Show the pencil in the fab
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit_white))

        // Show the Edit Profile Activity when clicking the fab
        fab.setOnClickListener {
            // Create the intent passing the fab position
            val editProfileIntent = Intent(this@LandingActivity, EditProfileActivity::class.java)
            editProfileIntent.putExtra(IntentUtils.FAB_X_POSITION, fab.right)
            editProfileIntent.putExtra(IntentUtils.FAB_Y_POSITION, fab.bottom)

            startActivityForResult(editProfileIntent, LandingActivity.REQUEST_EDIT)

            // Don't show animations, we'll handle that
            overridePendingTransition(0, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Save the device token of the user in the server if it hasn't already
        val deviceToken = FirebaseInstanceId.getInstance().token

        if (deviceToken != null) {
            Log.i("TOKEN", deviceToken)
            val userToken = SharedPreferencesUtils.getToken(this)!!
            SaveDeviceTokenObservable.create(userToken, deviceToken).subscribe(
                    {
                        // Nothing
                    },
                    {
                        // Nothing
                    }
            )
        }

        // Default action for the fab
        setupSearchFab()

        // Setup the view pager and the tab view
        pagerAdapter = TabUtils.createLandingPagerAdapter(this, supportFragmentManager, object : Interfaces.OnUserResult {
            override fun onUserResult(user: User) {
                // Save the profile visibility of the user
                val profilePublic = user.isProfilePublic

                if (profilePublic != null) {
                    SharedPreferencesUtils.setProfileVisibility(this@LandingActivity, profilePublic)
                }

                if (user.isAvailable!!) {
                    // Show the green button in the menu
                    menu?.getItem(0)?.setIcon(R.drawable.online_state)
                } else {
                    // Show the red button in the menu
                    menu?.getItem(0)?.setIcon(R.drawable.offline_state)
                }
            }
        })
        vpFragmentList.adapter = pagerAdapter
        tlTabs.setupWithViewPager(vpFragmentList)

        // Configure the off-screen of the view pager
        vpFragmentList.offscreenPageLimit = 2

        // Update the menu of the activity when navigating through the ViewPager
        vpFragmentList.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val pageTitle = pagerAdapter.getPageTitle(position)

                when (pageTitle) {
                    getString(R.string.main_feed_tab) -> setupSearchFab()
                    getString(R.string.main_notifications_tab) -> setupNotificationsFab()
                    getString(R.string.main_profile_tab) -> setupEditFab()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Nothing
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Nothing
            }
        })

        // Update the online state of the user
        val userToken = SharedPreferencesUtils.getToken(this)!!
        UpdateOnlineObservable.create(userToken, 1).subscribe(
                {
                    // Nothing
                },
                {
                    error ->

                    ErrorUtils.showNoConnectionDialog(this)
                }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == LandingActivity.REQUEST_EDIT && resultCode == EditProfileActivity.RESULT_UPDATED) {
            // The user has updated the data, so reload the profile
            val profileFragment = pagerAdapter.getItem(2) as ProfileFragment

            // Reload the profile
            profileFragment.loadUserData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Save the menu
        this.menu = menu

        // Inflate the menu of the base activity
        menuInflater.inflate(R.menu.menu_base_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val selectedId = item?.itemId

        if (selectedId == R.id.toolbarChangeAvailable) {
            // Get the token of the user
            val userToken = SharedPreferencesUtils.getToken(this)!!

            // Get the profile fragment to trigger an update
            val profileFragment = pagerAdapter.getItem(pagerAdapter.count - 1) as ProfileFragment

            ChangeAvailabilityDialog.createDialog(this,
                    {
                        // Change the availability to available
                        ChangeStatusObservable.create(userToken, true).subscribe(
                                {
                                    // Trigger an update in the user's profile
                                    profileFragment.loadUserData()

                                    // Show the green button in the menu
                                    menu?.getItem(0)?.setIcon(R.drawable.online_state)
                                },
                                {
                                    error ->

                                    ErrorUtils.showGeneralWhoopsDialog(this)
                                }
                        )
                    },
                    {
                        // Change the availability to not available
                        ChangeStatusObservable.create(userToken, false).subscribe(
                                {
                                    // Trigger an update in the user's profile
                                    profileFragment.loadUserData()

                                    // Show the red button in the menu
                                    menu?.getItem(0)?.setIcon(R.drawable.offline_state)
                                },
                                {
                                    error ->

                                    ErrorUtils.showGeneralWhoopsDialog(this)
                                }
                        )
                    }
            ).show()

            return true
        } else if (selectedId == R.id.toolbarEditSlots) {
            val editWithFocusIntent = Intent(this, EditProfileActivity::class.java)
            editWithFocusIntent.putExtra(IntentUtils.ANIMATIONS, false)
            editWithFocusIntent.putExtra(IntentUtils.REQUEST_SLOT_FOCUS, true)

            startActivityForResult(editWithFocusIntent, REQUEST_EDIT)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun hasTabs(): Boolean {
        return true
    }
}