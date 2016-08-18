package us.nowbe.nowbe.ui.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_feed.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.FeedAdapter
import us.nowbe.nowbe.model.exceptions.EmptyFeedException
import us.nowbe.nowbe.net.async.FeedObsevable
import us.nowbe.nowbe.ui.activities.ProfileActivity
import us.nowbe.nowbe.utils.*
import java.io.IOException

class FeedFragment() : Fragment() {

    /**
     * Adapter of the feed
     */
    lateinit var feedAdapter: FeedAdapter

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
     * Reference to the scroll listener that our recycler view will be using
     */
    val scrollListener: HideFabOnScroll by lazy {
        HideFabOnScroll(activity.findViewById(R.id.fab) as FloatingActionButton)
    }

    /**
     * Token of the user to load the feed
     */
    lateinit var token: String

    /**
     * Loads the data into the feed
     */
    fun loadData() {
        // Show the refreshing icon
        srlFeedRefresh.isRefreshing = true

        previousSubscription = FeedObsevable.create(token).subscribe(
                // On Next
                {
                    feed ->
                    // Hide the loading progress bar and show the recycler view
                    srlFeedRefresh.isRefreshing = false

                    // Update the adapter's feed
                    feedAdapter.updateFeed(feed.feedContent)

                    // If we have the empty activity showing and we have content, hide it
                    if (llEmptyFeed.visibility == View.VISIBLE && feedAdapter.itemCount > 0) {
                        llEmptyFeed.visibility = View.GONE
                    }
                },
                // On Error
                {
                    error ->
                    // Set an empty list to the adapter
                    feedAdapter.updateFeed(arrayListOf())

                    // Hide the loading progress
                    srlFeedRefresh.isRefreshing = false

                    if (error is EmptyFeedException) {
                        // Show a :( message
                        llEmptyFeed.visibility = View.VISIBLE
                    } else if (error is IOException) {
                        ErrorUtils.showNoConnectionToast(context)
                    }
                })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // Get the token
        token = SharedPreferencesUtils.getToken(context!!)!!
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_feed, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recycler view with an empty list
        feedAdapter = FeedAdapter()
        feedAdapter.onClick = object : Interfaces.OnFeedItemClick {
            override fun onFeedItemClick(itemSelected: Int) {
                // TODO: Allow the user to send a hello to the user from the feed

                // Get the selected user's token
                val selectedToken = feedAdapter.getFeedItem(itemSelected).token

                // Create an intent pointing to the profile activity
                val intent = Intent(activity, ProfileActivity::class.java)

                // Put the selected user's token as an extra
                intent.putExtra(ApiUtils.API_USER_TOKEN, selectedToken)

                // Start the activity
                startActivity(intent)
            }
        }
        rvFeed.adapter = feedAdapter
        rvFeed.layoutManager = LinearLayoutManager(context)

        // Hide the fab when scrolling the recycler view
        rvFeed.addOnScrollListener(scrollListener)

        // Load the feed in another thread
        loadData()

        // Implement the swipe to refresh feature
        srlFeedRefresh.setOnRefreshListener {
            // Refresh the adapter
            loadData()
        }
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}
