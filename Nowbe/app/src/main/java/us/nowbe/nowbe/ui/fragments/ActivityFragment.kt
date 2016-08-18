package us.nowbe.nowbe.ui.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_activity.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.ActivityAdapter
import us.nowbe.nowbe.model.exceptions.EmptyActivityException
import us.nowbe.nowbe.net.async.ActivityDataObservable
import us.nowbe.nowbe.net.async.RemoveActivityObservable
import us.nowbe.nowbe.net.async.RemoveAllActivityObservable
import us.nowbe.nowbe.ui.dialogs.DeleteActivityDialog
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.HideFabOnScroll
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class ActivityFragment : Fragment() {

    /**
     * Adapter of the activity
     */
    lateinit var activityAdapter: ActivityAdapter

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
     * Loads the activity of the user in another thread
     */
    fun loadActivity(adapter: ActivityAdapter) {
        // Show the refreshing icon
        srlActivityRefresh.isRefreshing = true

        // Get the token of the user
        val token = SharedPreferencesUtils.getToken(context)!!

        previousSubscription = ActivityDataObservable.create(token).subscribe(
                // On Next
                {
                    result ->

                    // Hide the refreshing icon
                    srlActivityRefresh.isRefreshing = false

                    // Refresh the adapter with the new data
                    adapter.updateActivity(result.activityContent)
                },
                // On Error
                {
                    error ->

                    // Hide the refreshing icon
                    srlActivityRefresh.isRefreshing = false

                    if (error is EmptyActivityException) {
                        Toast.makeText(context, "No activity for now!", Toast.LENGTH_SHORT).show()
                    } else {
                        ErrorUtils.showGeneralWhoopsDialog(context)
                    }
                }
        )
    }

    /**
     * Removes all the activity from the recycler view, notifies the server and the adapter
     */
    fun removeAllActivity() {
        // Get the token of the user
        val userToken = SharedPreferencesUtils.getToken(context)!!

        RemoveAllActivityObservable.create(userToken).subscribe(
                // On Next
                {
                    // Notify the adapter about the change
                    activityAdapter.removeAllActivity()
                },
                // On Error
                {
                    ErrorUtils.showGeneralWhoopsDialog(context)
                }
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater?.inflate(R.layout.fragment_activity, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup the recycler view
        activityAdapter = ActivityAdapter()
        activityAdapter.onLongClick = object : Interfaces.OnActivityDeleteClick {
            override fun onActivityDeleteClick(id: String, index: Int) {
                DeleteActivityDialog.createDialog(context, {
                    // Get the token of the user
                    val userToken = SharedPreferencesUtils.getToken(context)!!

                    // Remove the activity from the server
                    RemoveActivityObservable.create(userToken, id).subscribe(
                            // On Next
                            {
                                // Notify the adapter about the removal
                                activityAdapter.removeActivity(index)
                            },
                            // On Error
                            {
                                error ->

                                ErrorUtils.showGeneralWhoopsDialog(context)
                            }
                    )
                }).show()
            }
        }
        rvActivity.adapter = activityAdapter
        rvActivity.layoutManager = LinearLayoutManager(context)
        rvActivity.addOnScrollListener(scrollListener)

        // Load the data async
        loadActivity(activityAdapter)

        // Setup the refresh
        srlActivityRefresh.setOnRefreshListener {
            loadActivity(activityAdapter)
        }
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}