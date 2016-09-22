package us.nowbe.nowbe.ui.activities

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.model.exceptions.AlreadyPairedException
import us.nowbe.nowbe.ui.animation.CircularReveal
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.async.*
import us.nowbe.nowbe.ui.dialogs.*
import us.nowbe.nowbe.utils.*
import java.io.File
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {

    companion object {
        /**
         * Request and result codes
         */
        const val RESULT_UPDATED = 1
        const val REQUEST_SEARCH = 2
    }

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
     * Boolean indicating whether the reveal animation should be displayed or not
     */
    var isAnimationEnabled = true

    /**
     * Position of the fab
     */
    var fabX: Int = 0
    var fabY: Int = 0

    /**
     * File path of the temporary file
     */
    var tempFilePath: String = ""

    /**
     * Indicates whether the user chose to edit their profile picture (true) or a slot picture (false)
     */
    var isProfilePicture: Boolean = false

    /**
     * Indicates the slot that the user chose to modify
     */
    var slotIndex: Int = 0

    /**
     * Action to perform when the dialogs are dismissed
     */
    val onDismiss = object : Interfaces.OnDialogDismiss {
        override fun onDismiss() {
            // Re-load the user data
            loadUserData()

            // We have updated data of the user, so force a reload when finishing the activity
            setResult(EditProfileActivity.RESULT_UPDATED)
        }
    }

    /**
     * Loads the data of the user and populates the widgets with that data
     */
    fun loadUserData(onResult: (() -> Unit)? = null) {
        // Load the user data and populate the widgets with it
        val token = SharedPreferencesUtils.getToken(this)!!

        previousSubscription = UserDataObservable.create(token, token).subscribe(
                // On Next
                {
                    user ->

                    // Set the user photo
                    Glide.with(this)
                            .load(ApiUtils.getThumbProfilePicDir(user.profilePicDir))
                            .crossFade()
                            .transform(CircleTransform(this))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .error(R.drawable.nowbe_logo)
                            .into(ivUserPicture)

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

                    // Notify about the result
                    onResult?.invoke()
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
                    // Reload the data!
                    onDismiss.onDismiss()

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
                    } else if (error is IOException) {
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
                    // Reload the data!
                    onDismiss.onDismiss()

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
                    } else if (error is IOException) {
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

        // Get the extras from the intent
        val requestSlotsFocus = intent.extras.getBoolean(IntentUtils.REQUEST_SLOT_FOCUS, false)
        isAnimationEnabled = intent.extras.getBoolean(IntentUtils.ANIMATIONS, true)
        fabX = intent.extras.getInt(IntentUtils.FAB_X_POSITION, 0)
        fabY = intent.extras.getInt(IntentUtils.FAB_Y_POSITION, 0)

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

        // Load the data of the user
        loadUserData({
            // Request the focus of the slots if the activity that started the editing requests it
            if (requestSlotsFocus) {
                nsvEditProfile.post {
                    nsvEditProfile.smoothScrollTo(0, csvEditCommentsSlots.bottom)
                }
            }
        })

        // If the savedInstanceState is null we can ensure that the user is not returning from outside the app
        // and thus we don't show the animation again
        if (isAnimationEnabled && savedInstanceState == null) {
            clEditProfileRoot.post {
                // Show the circular reveal animation passing the fab position
                CircularReveal.showEnterRevealAnimation(clEditProfileRoot, { }, fabX, fabY)
            }
        } else {
            // If it's not null then we're returning from elsewhere (a rotation change for example), so show the view
            clEditProfileRoot.visibility = View.VISIBLE
        }

        // Setup the action of the edit profile photo
        ivUserProfilePic.setOnClickListener {
            // We're uploading a profile pic
            isProfilePicture = true

            SelectPictureSourceDialog.newInstance(object : Interfaces.OnTemporaryImagePath {
                override fun onImagePath(imagePath: String) {
                    // Save the temporary image file path
                    tempFilePath = imagePath
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

        llEditCouple.setOnClickListener {
            // Start the search activity and the result will come back in the form of an activity result
            val searchIntent = Intent(this, SearchActivity::class.java)
            searchIntent.putExtra(IntentUtils.SEARCH_RESULT, SearchActivity.Companion.SearchResultClick.RETURN_DATA)
            searchIntent.putExtra(IntentUtils.ANIMATIONS, false)
            startActivityForResult(searchIntent, REQUEST_SEARCH)
        }

        llEditCouple.setOnLongClickListener {
            // Get the token of the user
            val userToken = SharedPreferencesUtils.getToken(this)!!
            RemoveCoupleObservable.create(userToken).subscribe(
                    {
                        onDismiss.onDismiss()
                    },
                    {
                        ErrorUtils.showNoConnectionToast(this)
                    }
            )
            true
        }

        // Setup the action of clicking a pictures slot
        psvEditPicturesSlots.onClick = object : Interfaces.OnPictureSlotClick {
            override fun onPictureSlotClick(itemSelected: Int, adapterPosition: Int) {
                val showRemove = psvEditPicturesSlots.isSlotEmpty(itemSelected)

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
                }, showRemove, {
                    // Remove the current slot
                    val userToken = SharedPreferencesUtils.getToken(this@EditProfileActivity)!!

                    RemovePictureSlotObservable.create(userToken, itemSelected).subscribe(
                            {
                                onDismiss.onDismiss()
                            },
                            {
                                ErrorUtils.showGeneralWhoopsDialog(this@EditProfileActivity)
                            }
                    )
                }).show(supportFragmentManager, null)
            }
        }

        // Setup the action of clicking a comments slot
        csvEditCommentsSlots.onClick = object : Interfaces.OnCommentSlotClick {
            override fun onCommentSlotClick(commentText: String, commentIndex: Int) {
                val showRemove = commentText != ""

                CommentEditionDialog.newInstance(
                        {
                            EditCommentDialog.newInstance(onDismiss, commentIndex).show(supportFragmentManager, null)
                        },
                        {
                            // Remove the current slot
                            val userToken = SharedPreferencesUtils.getToken(this@EditProfileActivity)!!

                            RemoveCommentObservable.create(userToken, commentIndex).subscribe(
                                    {
                                        onDismiss.onDismiss()
                                    },
                                    {
                                        ErrorUtils.showGeneralWhoopsDialog(this@EditProfileActivity)
                                    }
                            )
                        }, showRemove).show(supportFragmentManager, null)
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
        } else if (requestCode == REQUEST_SEARCH && resultCode == SearchActivity.RESULT_USER_SELECTED) {
            // Get the search result from the intent data
            val searchResult = data?.getSerializableExtra(IntentUtils.SEARCH_RESULT) as SearchResult

            // Show the confirmation dialog
            SetCoupleDialog.createDialog(this, searchResult.fullname, {
                // Get the token of the user
                val userToken = SharedPreferencesUtils.getToken(this)!!

                // Update the couple of the user
                UpdateUserCoupleObservable.create(userToken, searchResult.username).subscribe(
                        {
                            Toast.makeText(this, getString(R.string.profile_edit_couple_updated), Toast.LENGTH_SHORT).show()
                            onDismiss.onDismiss()
                        },
                        {
                            error ->

                            if (error is RequestNotSuccessfulException) {
                                ErrorUtils.showGeneralWhoopsDialog(this)
                            } else if (error is AlreadyPairedException) {
                                ErrorUtils.alreadyPairedDialog(this)
                            } else {
                                ErrorUtils.showNoConnectionToast(this)
                            }
                        }
                )
            }).show()
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
        if (isAnimationEnabled) {
            // Show the exit reveal animation (and finish the activity)
            CircularReveal.showExitRevealAnimation(clEditProfileRoot, { finish() }, fabX, fabY)
        } else {
            finish()
        }
    }

    override fun finish() {
        super.finish()

        if (isAnimationEnabled) {
            // Don't show animations, we'll handle that
            overridePendingTransition(0, 0)
        }
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}