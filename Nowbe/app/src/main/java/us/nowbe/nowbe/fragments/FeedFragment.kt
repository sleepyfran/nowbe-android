package us.nowbe.nowbe.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_feed.*

import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.FeedAdapter

class FeedFragment : Fragment {
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
    }
}
