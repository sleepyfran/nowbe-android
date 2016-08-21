package us.nowbe.nowbe.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search_type.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.SearchResultsAdapter
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.net.async.NotifyAccessFromSearchObservable
import us.nowbe.nowbe.ui.activities.ProfileActivity
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SearchTypeFragment : Fragment() {
    /**
     * Adapter of the results
     */
    val resultsAdapter = SearchResultsAdapter()

    /**
     * Adds a result to the adapter
     */
    fun loadResults(content: MutableList<SearchResult>) {
        resultsAdapter.addResults(content)
    }

    /**
     * Shows the no results message
     */
    fun showNoResults() {
        rvSearchResults.visibility = View.GONE
        llNoResults.visibility = View.VISIBLE
    }

    /**
     * Hides the no results message
     */
    fun hideNoResults() {
        rvSearchResults.visibility = View.VISIBLE
        llNoResults.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_search_type, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recycler view to show search results
        resultsAdapter.onClick = object : Interfaces.OnSearchResultClick {
            override fun onSearchResultClick(selectedUserToken: String) {
                // Get the username of the user
                val userUsername = SharedPreferencesUtils.getUsername(context)!!

                // Send the notification of access from the search
                NotifyAccessFromSearchObservable.create(selectedUserToken, userUsername).subscribe()

                // Show the profile of the selected user
                val intent = Intent(activity, ProfileActivity::class.java)
                intent.putExtra(ApiUtils.API_USER_TOKEN, selectedUserToken)
                startActivity(intent)
            }
        }
        rvSearchResults.adapter = resultsAdapter
        rvSearchResults.layoutManager = LinearLayoutManager(context)
    }
}