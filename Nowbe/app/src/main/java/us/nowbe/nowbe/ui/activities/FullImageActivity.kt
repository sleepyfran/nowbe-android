package us.nowbe.nowbe.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_full_image.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.IntentUtils

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
        val imageUrl = intent?.extras?.getString(IntentUtils.IMG_DATA)!!
        val cools = intent?.extras?.getInt(IntentUtils.COOLS)

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

        // Load the cools
        tvPictureCools.text = getString(R.string.full_image_cools, cools)
    }
}