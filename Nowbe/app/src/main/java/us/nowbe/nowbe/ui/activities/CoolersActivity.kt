package us.nowbe.nowbe.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_coolers.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.UserListAdapter
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.net.async.GetCoolersObservable
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

class CoolersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coolers)

        // Get the token of the image poster from the intents
        val posterToken = intent?.extras?.getString(IntentUtils.TOKEN)
        val imgSlot = intent?.extras?.getString(IntentUtils.IMG_DATA)

        // Setup the recycler view
        val adapter = UserListAdapter()
        rvCoolers.adapter = adapter
        rvCoolers.layoutManager = LinearLayoutManager(this)

        // Open the profile of the user on click
        adapter.onClick = {
            user ->

            val profileIntent = Intent(this, ProfileActivity::class.java)
            profileIntent.putExtra(ApiUtils.API_USER_TOKEN, user.token)
            startActivity(profileIntent)
        }

        // Load the coolers and populate the adapter with them
        val userToken = SharedPreferencesUtils.getToken(this)!!
        GetCoolersObservable.create(userToken, "$posterToken$imgSlot.jpg")
                .map {
                    users ->

                    val coolers: MutableList<User> = mutableListOf()

                    for (i in 0..users.length() - 1) {
                        val cooler = users.getJSONObject(i)
                        val coolerToken = cooler.getString(ApiUtils.KEY_USER_TOKEN)
                        val coolerFullName = cooler.getString(ApiUtils.API_USER_FULLNAME)
                        val coolerPicture = cooler.getString(ApiUtils.KEY_PICTURE)

                        coolers.add(User(coolerToken, "", coolerFullName, false, "", coolerPicture, 0, "", "", 0, false, 0, "", "", "", ""))
                    }

                    coolers
                }.subscribe(
                {
                    users ->

                    adapter.updateContent(users)
                },
                {
                    error ->

                    ErrorUtils.showNoConnectionDialog(this)
                }
        )
    }
}