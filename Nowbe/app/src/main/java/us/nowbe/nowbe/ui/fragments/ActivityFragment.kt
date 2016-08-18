package us.nowbe.nowbe.ui.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
     * Shows the empty activity message
     */
    fun showEmptyActivityView() {
        llEmptyActivity.visibility = View.VISIBLE
    }

    /**
     * Hides the empty activity message
     */
    fun hideEmptyActivityView() {
        llEmptyActivity.visibility = View.GONE
    }

    /**
     * Loads the activity of the user in another thread
     */
    fun loadActivity() {
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
                    activityAdapter.updateActivity(result.activityContent)

                    // If we have the empty activity showing and we have content, hide it
                    if (llEmptyActivity.visibility == View.VISIBLE && activityAdapter.itemCount > 0) {
                        hideEmptyActivityView()
                    }
                },
                // On Error
                {
                    error ->

                    // Hide the refreshing icon
                    srlActivityRefresh.isRefreshing = false

                    if (error is EmptyActivityException) {
                        showEmptyActivityView()
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

        previousSubscription = RemoveAllActivityObservable.create(userToken).subscribe(
                // On Next
                {
                    // Notify the adapter about the change
                    activityAdapter.removeAllActivity()

                    // If we don't have any items, show the empty activity
                    if (activityAdapter.itemCount == 0) {
                        showEmptyActivityView()
                    }
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
                    previousSubscription = RemoveActivityObservable.create(userToken, id).subscribe(
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
        loadActivity()

        // Setup the refresh
        srlActivityRefresh.setOnRefreshListener {
            loadActivity()
        }
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}