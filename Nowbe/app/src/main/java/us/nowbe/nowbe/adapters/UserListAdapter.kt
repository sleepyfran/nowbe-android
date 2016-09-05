package us.nowbe.nowbe.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.holders.UserListHolder
import us.nowbe.nowbe.model.User

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class UserListAdapter : RecyclerView.Adapter<UserListHolder>() {
    /**
     * List of content to display
     */
    var users: MutableList<User> = arrayListOf()

    /**
     * Method to call when the user presses a holder
     */
    var onClick: ((user: User) -> Unit)? = null

    /**
     * Updates the activity with new content and notifies about the change
     */
    fun updateContent(content: MutableList<User>) {
        users.clear()
        users.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_coolers, parent, false)
        val holder = UserListHolder(view)

        // Set the holder on click listener
        holder.itemView.setOnClickListener {
            onClick?.invoke(users[holder.adapterPosition])
        }

        return holder
    }

    override fun onBindViewHolder(holder: UserListHolder, position: Int) {
        holder.update(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}