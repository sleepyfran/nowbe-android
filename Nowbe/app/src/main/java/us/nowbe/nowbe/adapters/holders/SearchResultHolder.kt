package us.nowbe.nowbe.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_search_result.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.CircleTransform

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SearchResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Binds the data into the item
     */
    fun bindResult(result: SearchResult) {
        Glide.with(itemView.context)
                .load(ApiUtils.getThumbProfilePicDir(result.profilePic))
                .crossFade()
                .error(R.drawable.nowbe_logo)
                .transform(CircleTransform(itemView.context))
                .into(itemView.ivUserPicture)

        itemView.tvUserFullName.text = result.fullname
        itemView.tvUsername.text = result.username
        itemView.tvUserAbout.text = result.about

        // Show the lock icon if the profile is locked
        if (result.isPrivate) {
            itemView.ivPrivateProfile.visibility = View.VISIBLE
        } else {
            itemView.ivPrivateProfile.visibility = View.GONE
        }
    }
}