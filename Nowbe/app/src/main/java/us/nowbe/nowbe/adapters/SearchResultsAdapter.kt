package us.nowbe.nowbe.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.holders.SearchResultHolder
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.utils.Interfaces

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultHolder>() {
    /**
     * List of content to display
     */
    var results: MutableList<SearchResult> = arrayListOf()

    /**
     * Interface to be called when the user presses an item
     */
    lateinit var onClick: Interfaces.OnSearchResultClick

    /**
     * Adds a result to the list and notifies the adapter
     */
    fun addResults(content: MutableList<SearchResult>) {
        results.clear()
        results.addAll(content)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val holder = SearchResultHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_search_result, parent, false))

        // Set the holder on click listener only if the profile is not private or their relationship is mutual
        holder.itemView.setOnClickListener({
            val result = results[holder.adapterPosition]

            onClick.onSearchResultClick(result)
        })

        return holder
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        holder.bindResult(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }
}