package us.nowbe.nowbe.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.pictures_slot_view.*
import kotlinx.android.synthetic.main.profile_information_view.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.net.NowbeUserData
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class ProfileFragment : Fragment() {
    /**
     * Token to which the profile refers to
     */
    lateinit var token: String

    companion object {
        /**
         * Returns a new instance with the specified token as the one to use
         */
        fun newInstance(token: String): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.token = token
            return fragment
        }
    }

    fun loadProfileData() {
        // Load the user in another thread
        object : AsyncTask<Void, Void, User>() {
            override fun doInBackground(vararg params: Void?): User {
                return NowbeUserData(token).getUser()!!
            }

            override fun onPostExecute(user: User?) {
                // Update the user's profile pic
                Picasso.with(context).load(user?.profilePicDir).noFade().into(ivUserPicture)

                // Update the user's online state
                Picasso.with(context).load(
                        (if (user?.status!!) R.drawable.online_state else R.drawable.offline_state)
                ).into(ivUserState)

                // Update the user's username
                tvUserUsername.text = getString(R.string.profile_username, user?.nickname!!)

                // Update the user's about section
                tvUserAbout.text = user?.about

                // Update the user's statistics
                tvUserInfo.text = getString(R.string.profile_info, user?.age, user?.visits, user?.friends)

                // Set up the pictures slots
                psvPicturesSlots.updateSlots(user!!)
            }
        }.execute()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // Get the token
        token = SharedPreferencesUtils.getToken(context!!)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_profile, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load the profile data
        loadProfileData()
    }
}
