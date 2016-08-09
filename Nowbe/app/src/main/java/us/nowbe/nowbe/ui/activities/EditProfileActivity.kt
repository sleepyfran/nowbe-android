package us.nowbe.nowbe.ui.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.animation.CircularReveal
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.async.UpdateUserAvatarObservable
import us.nowbe.nowbe.net.async.UpdateUserSlotObservable
import us.nowbe.nowbe.ui.dialogs.*
import us.nowbe.nowbe.net.async.UserDataObservable
import us.nowbe.nowbe.utils.*
import java.io.File

class EditProfileActivity : AppCompatActivity() {
    /**
     * Previous subscription
     */
    var previousSubscription: Subscription? = null
        set(value) {
            // Unsubscribe the previous subscription before overriding it
            field?.unsubscribe()
            field = value
        }

    /**
     * Position of the fab
     */
    var fabX: Int = 0
    var fabY: Int = 0

    /**
     * File path of the temporary file
     */
    lateinit var tempFilePath: String

    /**
     * Indicates whether the user chose to edit their profile picture (true) or a slot picture (false)
     */
    var isProfilePicture: Boolean = false

    /**
     * Indicates the slot that the user chose to modify
     */
    var slotIndex: Int = 0

    /**
     * Loads the data of the user and populates the widgets with that data
     */
    fun loadUserData(forceRefresh: Boolean) {
        // Load the user data and populate the widgets with it
        val token = SharedPreferencesUtils.getToken(this)!!

        previousSubscription = UserDataObservable.create(token, token).subscribe(
                // On Next
                {
                    user ->

                    // Set the user photo
                    Picasso.with(this).load(ApiUtils.getThumbProfilePicDir(user.profilePicDir, forceRefresh)).noFade().into(ivUserPicture)

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

                    // Load the pictures
                    psvEditPicturesSlots.updateSlots(user)

                    // Load the number of pictures
                    tvEditPicturesSlotsDescription.text =
                            getString(R.string.profile_edit_slots_description,
                                    user.picturesSlots.filter { it?.data != ApiUtils.NULL }.size)

                    // Load the number of comments
                    tvEditCommentsSlotsDescription.text =
                            getString(R.string.profile_edit_slots_description,
                                    user.commentsSlots.filter { it?.data != "" }.size)

                    // Load the comments
                    csvEditCommentsSlots.updateSlots(user)
                },
                // On Error
                {
                    error ->
                    ErrorUtils.showGeneralWhoopsDialog(this)
                }
        )
    }

    /**
     * Uploads a profile picture photo to the server
     */
    fun uploadProfilePicture(token: String, tempFile: File) {
        // Upload it!
        previousSubscription = UpdateUserAvatarObservable.create(token, tempFile).subscribe(
                // On Next
                {
                    // Show the new photo
                    ivUserPicture.setImageBitmap(BitmapFactory.decodeFile(tempFilePath))

                    // Show a toast confirming the change
                    Toast.makeText(this, getString(R.string.profile_edit_avatar_updated), Toast.LENGTH_SHORT).show()

                    // Delete the temporary file
                    InternalStorageUtils.deleteTemporaryImagefile(this, tempFilePath)
                },
                // On Error
                {
                    error ->

                    if (error is RequestNotSuccessfulException) {
                        // Show a general error, we don't know why we got the 0!
                        ErrorUtils.showGeneralWhoopsDialog(this)
                    } else {
                        ErrorUtils.showNoConnectionToast(this)
                    }
                }
        )
    }

    /**
     * Uploads a slot picture to the server
     */
    fun uploadSlotPicture(token: String, file: File) {
        previousSubscription = UpdateUserSlotObservable.create(token, file, slotIndex).subscribe(
                // On Next
                {
                    // Show a toast confirming the change
                    Toast.makeText(this, getString(R.string.profile_edit_pictures_slots_updated, slotIndex + 1),
                            Toast.LENGTH_SHORT).show()

                    // Delete the temporary file
                    InternalStorageUtils.deleteTemporaryImagefile(this, tempFilePath)
                },
                // On Error
                {
                    error ->

                    if (error is RequestNotSuccessfulException) {
                        // Show a general error, we don't know why we got the 0!
                        ErrorUtils.showGeneralWhoopsDialog(this)
                    } else {
                        ErrorUtils.showNoConnectionToast(this)
                    }
                }
        )
    }

    /**
     * Uploads a photo to the server
     */
    fun handlePhoto() {
        // Get the token of the user
        val token = SharedPreferencesUtils.getToken(this)!!

        // Get the temporary file
        val tempFile = File(tempFilePath)

        if (isProfilePicture) {
            uploadProfilePicture(token, tempFile)
        } else {
            uploadSlotPicture(token, tempFile)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Load the data of the user
        loadUserData(false)

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
        } else {
            // If it's not null then we're returning from elsewhere (a rotation change for example), so show the view
            clEditProfileRoot.visibility = View.VISIBLE
        }

        // Action to perform when the dialogs are dismissed
        val onDismiss = object : Interfaces.OnDialogDismiss {
            override fun onDismiss() {
                // Re-load the user data
                loadUserData(true)
            }
        }

        // Setup the action of the edit profile photo
        ivUserProfilePic.setOnClickListener {
            SelectPictureSourceDialog.newInstance(object : Interfaces.OnTemporaryImagePath {
                override fun onImagePath(imagePath: String) {
                    // Save the temporary image file path
                    tempFilePath = imagePath

                    // We're uploading a profile pic
                    isProfilePicture = true
                }
            }).show(supportFragmentManager, null)
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

        // Setup the action of the education button
        llEditEducation.setOnClickListener {
            EditEducationDialog.newInstance(onDismiss, tvEditEducation.text.toString())
                    .show(supportFragmentManager, null)
        }

        // Setup the action of clicking a pictures slot
        psvEditPicturesSlots.onClick = object : Interfaces.OnPictureSlotClick {
            override fun onPictureSlotClick(itemSelected: Int) {
                // Save the index
                slotIndex = itemSelected

                // We are not uploading the profile pic
                isProfilePicture = false

                // Show the pick photo bottom sheet
                SelectPictureSourceDialog.newInstance(object : Interfaces.OnTemporaryImagePath {
                    override fun onImagePath(imagePath: String) {
                        // Save the temporary image file path
                        tempFilePath = imagePath
                    }
                }).show(supportFragmentManager, null)
            }
        }

        // Setup the action of clicking a comments slot
        csvEditCommentsSlots.onClick = object : Interfaces.OnCommentSlotClick {
            override fun onCommentSlotClick(itemSelected: Int) {
                EditCommentDialog.newInstance(onDismiss, itemSelected).show(supportFragmentManager, null)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IntentUtils.REQUEST_CAMERA_IMAGE && resultCode == Activity.RESULT_OK) {
            // Allow the user to crop the image
            CropUtils.startSquareCroppingActivityFromFilePath(this, tempFilePath)
        } else if (requestCode == IntentUtils.REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK) {
            // Allow the user to crop the image that they selected
            CropUtils.startSquareCroppingActivityFromUri(this, data?.data!!)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Get the URI of the cropped image
            val croppedImage = CropImage.getActivityResult(data).uri

            // Change the path of the file to the cropped one
            tempFilePath = croppedImage.path

            // Upload the image once we have it cropped
            handlePhoto()
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
        // Show the exit reveal animation (and finish the activity)
        CircularReveal.showExitRevealAnimation(clEditProfileRoot, { finish() }, fabX, fabY)
    }

    override fun finish() {
        super.finish()

        // Don't show animations, we'll handle that
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}