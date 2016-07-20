package us.nowbe.nowbe.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_feed.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.activities.ProfileActivity
import us.nowbe.nowbe.adapters.FeedAdapter
import us.nowbe.nowbe.model.Feed
import us.nowbe.nowbe.net.NowbeFeedData
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.OnClick
import us.nowbe.nowbe.utils.SharedPreferencesUtils

class FeedFragment : Fragment {
    /**
     * Reference to the scroll listener that our recycler view will be using
     */
    val scrollListener: OnScrollListener by lazy {
        OnScrollListener(activity.findViewById(R.id.fab) as FloatingActionButton)
    }

    /**
     * Token of the user to load the feed
     */
    lateinit var token: String

    constructor() : super()

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
        val adapter = FeedAdapter()
        adapter.onClick = object : OnClick.OnFeedItemClick {
            override fun onFeedItemClick(itemSelected: Int) {
                // TODO: Allow the user to send a hello to the user from the feed

                // Get the selected user's token
                val selectedToken = adapter.getFeedItem(itemSelected).token

                // Create an intent pointing to the profile activity
                val intent = Intent(activity, ProfileActivity::class.java)

                // Put the selected user's token as an extra
                intent.putExtra(ApiUtils.API_USER_TOKEN, selectedToken)

                // Start the activity
                startActivity(intent)
            }
        }
        rvFeed.adapter = adapter
        rvFeed.layoutManager = LinearLayoutManager(context)

        // Hide the fab when scrolling the recycler view
        rvFeed.addOnScrollListener(scrollListener)

        // Load the feed in another thread
        object : AsyncTask<Void, Void, Feed?>() {
            override fun doInBackground(vararg args: Void?): Feed? {
                return NowbeFeedData(token).getFeed()
            }

            override fun onPostExecute(feed: Feed?) {
                if (feed != null) {
                    // Hide the loading progress bar and show the recycler view
                    pbLoadingFeed.visibility = View.GONE
                    rvFeed.visibility = View.VISIBLE

                    // Update the adapter's feed
                    adapter.updateFeed(feed.feedContent)
                } else {
                    ErrorUtils.showNoConnectionToast(context)
                }
            }
        }.execute()
    }

    /**
     * Scroll listener to be used when the recycler view is scrolled
     */
    class OnScrollListener(val fab: FloatingActionButton) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0) {
                fab.hide()
            } else if (dy < 0) {
                fab.show()
            }
        }
    }
}
