package us.nowbe.nowbe.fragments

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

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
import us.nowbe.nowbe.adapters.FeedAdapter

class FeedFragment : Fragment {
    /**
     * Reference to the scroll listener that our recycler view will be using
     */
    val scrollListener: OnScrollListener by lazy {
        OnScrollListener(activity.findViewById(R.id.fab) as FloatingActionButton)
    }

    constructor() : super()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_feed, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recycler view
        val adapter = FeedAdapter()
        rvFeed.adapter = adapter
        rvFeed.layoutManager = LinearLayoutManager(context)

        // Hide the fab when scrolling the recycler view
        rvFeed.addOnScrollListener(scrollListener)
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
