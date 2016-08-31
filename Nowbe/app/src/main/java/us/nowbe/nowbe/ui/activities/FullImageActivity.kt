package us.nowbe.nowbe.ui.activities

import android.os.Bundle
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_full_image.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.net.async.CoolPictureObservable
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.IntentUtils
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class FullImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        // Get the image's URL and the token from the intent's extras
        val profileToken = intent?.extras?.getString(IntentUtils.TOKEN)!!
        val imageUrl = intent?.extras?.getString(IntentUtils.IMG_DATA)!!
        val showCoolbar = intent?.extras?.getBoolean(IntentUtils.SHOW_COOL_BAR, true)!!
        var cools = 0
        var pictureIndex = 0

        if (showCoolbar) {
            cools = intent?.extras?.getInt(IntentUtils.COOLS)!!
            pictureIndex = intent?.extras?.getInt(IntentUtils.PIC_INDEX)!!
        }

        // Load the image into the placeholder
        Glide.with(this)
                .load(ApiUtils.getFullSlotPicDir(imageUrl))
                .crossFade()
                .listener(object : RequestListener<String, GlideDrawable> {
                    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        // Hide the loading progressbar
                        pbLoadingImage.visibility = View.GONE

                        return false
                    }

                    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
                .into(ivtFullImage)

        if (showCoolbar) {
            // Load the cools
            tvPictureCools.text = getString(R.string.full_image_cools, cools)

            // TODO: Show the colored button if the user has already cooled the photo
            // Make a cool on the photo when the user presses the cool button
            ivCoolPicture.setOnClickListener {
                // Get the token of the user
                val userToken = SharedPreferencesUtils.getToken(this)!!

                CoolPictureObservable.create(userToken, profileToken, pictureIndex).subscribe(
                        {
                            // Update the cools counter
                            tvPictureCools.text = getString(R.string.full_image_cools, cools + 1)

                            // Set the color of the button
                            DrawableCompat.setTint(ivCoolPicture.drawable, resources.getColor(R.color.accent))
                        },
                        {
                            error ->

                            ErrorUtils.showGeneralWhoopsDialog(this)
                        }
                )
            }
        } else {
            llCoolBar.visibility = View.GONE
        }
    }
}