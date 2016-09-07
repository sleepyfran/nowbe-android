package us.nowbe.nowbe.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import de.psdev.licensesdialog.LicensesDialog
import kotlinx.android.synthetic.main.activity_settings.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.net.async.ChangeProfileVisibilityObservable
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SettingsActivity : AppCompatActivity() {

    /**
     * Changes the text of the description in the visibility settings
     */
    fun changeVisibilityText(status: Boolean) {
        if (status) {
            tvVisibilityState.text = getString(R.string.settings_profile_visibility_message_visible)
        } else {
            tvVisibilityState.text = getString(R.string.settings_profile_visibility_message_invisible)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Setup the toolbar
        toolbar.title = getString(R.string.settings_title)

        // Setup the toolbar back button drawable
        val closeButton = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp)

        // Set the toolbar as the support action bar and display the close drawable
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(closeButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the elevation of the cards
        ViewCompat.setElevation(llSettingsPrivacy, 5.toFloat())
        ViewCompat.setElevation(llSettingsNotifications, 5.toFloat())
        ViewCompat.setElevation(llSettingsAbout, 5.toFloat())

        // Load the version of the app
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            tvAboutAppVersion.text = getString(R.string.settings_about_version_release, packageInfo.versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            // This should never EVER happen, but as everything in life...
            e.printStackTrace()
            // ... a printStackTrace never hurt anyone
        }

        // Load if the profile of the user is visible or not
        val profilePublic = SharedPreferencesUtils.getProfileVisibility(this)!!
        changeVisibilityText(profilePublic)
        sSettingsProfileVisibility.isChecked = profilePublic

        // Invert the switch when the view is clicked
        llProfileVisibility.setOnClickListener {
            val currentStatus = sSettingsProfileVisibility.isChecked
            sSettingsProfileVisibility.isChecked = !currentStatus
        }

        // Update the visibility of the user when the switch changes
        sSettingsProfileVisibility.setOnCheckedChangeListener {
            compoundButton, state ->

            // Update the settings in the phone
            SharedPreferencesUtils.setProfileVisibility(this, state)

            // Update the text
            changeVisibilityText(state)

            // Update the server
            val userToken = SharedPreferencesUtils.getToken(this)!!
            val visibility = if (state) 1 else 0
            ChangeProfileVisibilityObservable.create(userToken, visibility).subscribe(
                    {
                        if (state) {
                            Toast.makeText(this, getString(R.string.settings_profile_visibility_now_hidden), Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, getString(R.string.settings_profile_visibility_now_hidden), Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        error ->

                        ErrorUtils.showGeneralWhoopsDialog(this)
                    }
            )
        }

        // Log out the user when they press the log out button
        llProfileLogOut.setOnClickListener {
            // Show a dialog asking for confirmation
            AlertDialog.Builder(this)
                    .setMessage(getString(R.string.log_out_message))
                    .setPositiveButton(getString(R.string.log_out_yes), {
                        dialog, integer ->

                        // Clear the data and show the login
                        SharedPreferencesUtils.clearData(this)
                        val loginIntent = Intent(this, WizardActivity::class.java)
                        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(loginIntent)
                    })
                    .setNegativeButton(getString(R.string.log_out_no), null)
                    .create()
                    .show()
        }

        // Show the licenses when the button is pressed
        llSettingsLicenses.setOnClickListener {
            LicensesDialog.Builder(this)
                    .setNotices(R.raw.notices)
                    .build()
                    .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle the back button in the support action bar
        when (item?.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}