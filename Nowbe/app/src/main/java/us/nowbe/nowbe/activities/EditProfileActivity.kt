package us.nowbe.nowbe.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.animation.CircularReveal
import us.nowbe.nowbe.dialogs.EditAboutDialog
import us.nowbe.nowbe.dialogs.EditBirthdayDialog
import us.nowbe.nowbe.dialogs.EditInterestsDialog
import us.nowbe.nowbe.dialogs.EditVisibleNameDialog
import us.nowbe.nowbe.net.async.UserDataObservable
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.IntentUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class EditProfileActivity : AppCompatActivity() {
    /**
     * Position of the fab
     */
    var fabX: Int = 0
    var fabY: Int = 0

    /**
     * Loads the data of the user and populates the widgets with that data
     */
    fun loadUserData() {
        // Load the user data and populate the widgets with it
        val token = SharedPreferencesUtils.getToken(this)!!
        UserDataObservable.create(token).subscribe(
                // On Next
                {
                    user ->

                    // Set the user photo
                    Picasso.with(this).load(user.profilePicDir).noFade().into(ivUserPicture)

                    // Set the user's username
                    tvEditUsername.text = getString(R.string.profile_username, user.username)

                    // Load the user's name
                    tvEditVisibleName.text = user.fullName

                    // Load the user's about text
                    tvEditAbout.text = user.about

                    // Load the user's birthday
                    tvEditBirthday.text = user.birthday

                    // Load the user's interests
                    if (user.interests != null) {
                        tvEditInterests.text = user.interests
                    } else {
                        tvEditInterests.text = getString(R.string.profile_edit_unset)
                    }

                    // Load the user's academic info
                    if (user.education != null) {
                        tvEditEducation.text = user.education
                    } else {
                        tvEditEducation.text = getString(R.string.profile_edit_unset)
                    }

                    // Load the user's couple (if any)
                    if (user.coupleName != null) {
                        tvEditCouple.text = user.coupleName
                    } else {
                        tvEditCouple.text = getString(R.string.profile_edit_unset)
                    }

                    // Load the number of pictures and comments
                    tvEditPicturesSlotsDescription.text =
                            getString(R.string.profile_edit_slots_description,
                                    user.picturesSlots.filter { it?.data != ApiUtils.NULL }.size)
                    tvEditCommentsSlotsDescription.text =
                            getString(R.string.profile_edit_slots_description,
                                    user.commentsSlots.filter { it?.data != "" }.size)
                },
                // On Error
                {
                    error ->
                    ErrorUtils.showGeneralWhoopsDialog(this)
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Load the data of the user
        loadUserData()

        // Get the extras (fab position) from the intent
        fabX = intent.extras.getInt(IntentUtils.FAB_X_POSITION)
        fabY = intent.extras.getInt(IntentUtils.FAB_Y_POSITION)

        // Setup the toolbar title
        toolbar.title = getString(R.string.profile_edit_title)

        // Setup the toolbar background and title color
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.accent))
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white))

        // Setup the toolbar back button drawable
        val closeButton = ContextCompat.getDrawable(this, R.drawable.ic_close_white)

        // Set the toolbar as the support action bar and display the close drawable
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(closeButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup the elevation of the cards
        ViewCompat.setElevation(llEditBasicInfoCard, 5.toFloat())
        ViewCompat.setElevation(llEditSpecificInfoCard, 5.toFloat())
        ViewCompat.setElevation(llEditSlotsCard, 5.toFloat())

        // If the savedInstanceState is null we can ensure that the user is not returning from outside the app
        // and thus we don't show the animation again
        if (savedInstanceState == null) {
            clEditProfileRoot.post {
                // Show the circular reveal animation passing the fab position
                CircularReveal.showEnterRevealAnimation(clEditProfileRoot, { }, fabX, fabY)
            }
        }

        // Action to perform when the dialogs are dismissed
        // TODO: Check if we can ONLY do this when the user pressed the cancel button or pressed back
        val onDismiss = object : DialogInterface.OnDismissListener {
            override fun onDismiss(dialog: DialogInterface?) {
                // Re-load the user data
                loadUserData()
            }
        }

        // Setup the action of the visible name button
        llEditVisibleName.setOnClickListener {
            EditVisibleNameDialog.newInstance(onDismiss, tvEditVisibleName.text.toString())
                    .show(supportFragmentManager, null)
        }

        // Setup the action of the about button
        llEditAboutUser.setOnClickListener {
            EditAboutDialog.newInstance(onDismiss, tvEditAbout.text.toString())
                    .show(supportFragmentManager, null)
        }

        // Setup the action of the birthday button
        llEditBirthday.setOnClickListener {
            EditBirthdayDialog.newInstance(onDismiss).show(supportFragmentManager, null)
        }

        // Setup the action of the interests button
        llEditInterests.setOnClickListener {
            EditInterestsDialog.newInstance(onDismiss, tvEditInterests.text.toString())
                    .show(supportFragmentManager, null)
        }

        // TODO: Setup the action of the education button
        llEditEducation.setOnClickListener {

        }

        // TODO: Setup the action of the couple button
        llEditCouple.setOnClickListener {

        }

        // TODO: Setup the action of the pictures slots
        llEditPictures.setOnClickListener {

        }

        // TODO: Setup the action of the comments slots
        llEditComments.setOnClickListener {

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle the back button in the support action bar
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // Show the exit reveal animation (and finish the activity) TODO: Show a dialog confirming the discard
        CircularReveal.showExitRevealAnimation(clEditProfileRoot, { finish() }, fabX, fabY)
    }

    override fun finish() {
        super.finish()

        // Don't show animations, we'll handle that
        overridePendingTransition(0, 0)
    }
}