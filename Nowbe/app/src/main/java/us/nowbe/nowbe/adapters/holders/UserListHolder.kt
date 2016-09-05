package us.nowbe.nowbe.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_coolers.view.*
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.CircleTransform

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class UserListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Updates the content of the view that cooled the photo
     */
    fun update(user: User) {
        // Load the data of the user
        Glide.with(itemView.context)
                .load(ApiUtils.getThumbProfilePicDir(user.profilePicDir))
                .transform(CircleTransform(itemView.context))
                .into(itemView.ivUserPicture)

        itemView.tvUserFullName.text = user.fullName
    }
}