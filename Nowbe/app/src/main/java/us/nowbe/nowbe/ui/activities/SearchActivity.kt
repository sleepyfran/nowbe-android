package us.nowbe.nowbe.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import kotlinx.android.synthetic.main.activity_search.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.model.exceptions.NoResultsException
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.NowbeSearchUser
import us.nowbe.nowbe.net.async.AddUserObservable
import us.nowbe.nowbe.net.async.NotifyAccessFromSearchObservable
import us.nowbe.nowbe.net.async.SearchUserObservable
import us.nowbe.nowbe.ui.animation.CircularReveal
import us.nowbe.nowbe.ui.dialogs.AddPrivateProfileDialog
import us.nowbe.nowbe.ui.fragments.SearchTypeFragment
import us.nowbe.nowbe.utils.*
import java.io.IOException

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class SearchActivity : AppCompatActivity() {

    companion object {
        /**
         * Request and result codes
         */
        const val RESULT_USER_SELECTED = 1

        enum class SearchResultClick {
            OPEN_PROFILE, RETURN_DATA
        }
    }

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
     * Boolean indicating whether the reveal animation should be displayed or not
     */
    var isAnimationEnabled = true

    /**
     * Type of search result that the user wants
     */
    var typeOfSearch: SearchResultClick = SearchResultClick.OPEN_PROFILE

    /**
     * Position of the fab
     */
    var fabX: Int = 0
    var fabY: Int = 0

    /**
     * Token of the user using the app
     */
    lateinit var userToken: String

    /**
     * Current fragment we're in
     */
    lateinit var currentFragment: SearchTypeFragment

    /**
     * Last query that was searched
     */
    var lastQuery: String? = null

    /**
     * Current type of search the user wants
     */
    var currentTypeOfSearch = NowbeSearchUser.Companion.Type.USER

    /**
     * Searches for the user and updates the current fragment with that data
     */
    fun search() {
        if (lastQuery == null) return

        previousSubscription = SearchUserObservable.create(currentTypeOfSearch, userToken, lastQuery).subscribe(
                // On Next
                {
                    result ->

                    currentFragment.hideNoResults()

                    // Load the results into the current fragment
                    currentFragment.loadResults(result)
                },
                // On Error
                {
                    error ->

                    if (error is NoResultsException) {
                        currentFragment.showNoResults()
                    } else {
                        ErrorUtils.showNoConnectionDialog(this)
                    }
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Get the extras from the intent
        typeOfSearch = intent.getSerializableExtra(IntentUtils.SEARCH_RESULT) as SearchResultClick
        isAnimationEnabled = intent.extras.getBoolean(IntentUtils.ANIMATIONS, true)
        fabX = intent.extras.getInt(IntentUtils.FAB_X_POSITION, 0)
        fabY = intent.extras.getInt(IntentUtils.FAB_Y_POSITION, 0)

        // Load the token of the user
        userToken = SharedPreferencesUtils.getToken(this)!!

        // Setup the toolbar background and title color
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.accent))

        // Set the toolbar as the support action bar and display the close drawable
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Setup the tab layout and its color
        tlTabs.setBackgroundColor(ContextCompat.getColor(this, R.color.accent))

        // If the savedInstanceState is null we can ensure that the user is not returning from outside the app
        // and thus we don't show the animation again
        if (isAnimationEnabled && savedInstanceState == null) {
            clSearchRoot.post {
                // Show the circular reveal animation passing the fab position
                CircularReveal.showEnterRevealAnimation(clSearchRoot, { }, fabX, fabY)
            }
        } else {
            // If it's not null then we're returning from elsewhere (a rotation change for example), so show the view
            clSearchRoot.visibility = View.VISIBLE
        }

        // Setup the view pager and the tab view
        val pagerAdapter = TabUtils.createSearchPagerAdapter(this, supportFragmentManager, object : Interfaces.OnSearchResultClick {
            override fun onSearchResultClick(searchResult: SearchResult) {
                // Check the type of return that we want
                if (typeOfSearch == SearchResultClick.OPEN_PROFILE) {
                    // Get the username of the user
                    val userUsername = SharedPreferencesUtils.getUsername(this@SearchActivity)!!
                    val userToken = SharedPreferencesUtils.getToken(this@SearchActivity)!!

                    // Check if we can access the profile (is mutual or the profile is not private)
                    if (!searchResult.isPrivate || searchResult.relation == ApiUtils.API_FRIENDSHIP_MUTUAL) {
                        // Send the notification of access from the search
                        NotifyAccessFromSearchObservable.create(searchResult.token, userUsername).subscribe(
                                {
                                    // Nothing
                                },
                                {
                                    error ->
                                }
                        )

                        // Start the profile activity
                        val profileIntent = Intent(this@SearchActivity, ProfileActivity::class.java)
                        profileIntent.putExtra(ApiUtils.KEY_TOKEN, searchResult.token)
                        startActivity(profileIntent)
                    } else {
                        // If not then check if the user has added already the private profile
                        if (searchResult.relation == ApiUtils.API_FRIENDSHIP_SEMI_MUTUAL) {
                            // If so, show a dialog inviting the user to patiently wait for the other user to add him
                            AlertDialog.Builder(this@SearchActivity)
                                    .setTitle(getString(R.string.profile_add_private_profile_title))
                                    .setMessage(getString(R.string.profile_private_profile_wait))
                                    .setPositiveButton(getString(R.string.general_ok), null)
                                    .create()
                                    .show()
                        } else {
                            // Ask the user to send a friend request to the user
                            AddPrivateProfileDialog.createDialog(this@SearchActivity, {
                                AddUserObservable.create(userToken, searchResult.token).subscribe(
                                        // On Next
                                        {
                                            result ->

                                            // If the user was added, show a toast and update the fab
                                            Toast.makeText(this@SearchActivity, getString(R.string.profile_user_added, searchResult.fullname), Toast.LENGTH_LONG).show()
                                        },
                                        // On Error
                                        {
                                            error ->

                                            if (error is RequestNotSuccessfulException) {
                                                ErrorUtils.showUserNotAddedToast(this@SearchActivity)
                                            } else if (error is IOException) {
                                                ErrorUtils.showNoConnectionDialog(this@SearchActivity)
                                            }
                                        }
                                )
                            }).show()
                        }
                    }
                } else if (typeOfSearch == SearchResultClick.RETURN_DATA) {
                    // Pass the search result as the result of the activity
                    val resultIntent = Intent()
                    resultIntent.putExtra(IntentUtils.SEARCH_RESULT, searchResult)
                    setResult(SearchActivity.RESULT_USER_SELECTED, resultIntent)
                    finish()
                }
            }
        })
        vpSearchTypeList.adapter = pagerAdapter
        tlTabs.setupWithViewPager(vpSearchTypeList)

        // Configure the off-screen of the view pager
        vpSearchTypeList.offscreenPageLimit = 2

        // Load the default fragment
        currentFragment = pagerAdapter.getItem(0) as SearchTypeFragment

        // Listen to page changes so we can change the type of search
        vpSearchTypeList.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val pageTitle = pagerAdapter.getPageTitle(position)

                currentFragment = pagerAdapter.getItem(position) as SearchTypeFragment

                when (pageTitle) {
                    getString(R.string.search_title_users) -> currentTypeOfSearch = NowbeSearchUser.Companion.Type.USER
                    getString(R.string.search_title_interests) -> currentTypeOfSearch = NowbeSearchUser.Companion.Type.INTERESTS
                    getString(R.string.search_title_terms) -> currentTypeOfSearch = NowbeSearchUser.Companion.Type.TERM
                }

                // Trigger a search with the last query
                search()
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Nothing
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // Nothing
            }
        })

        fsvSearchUser.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                // Change the last query so we can access it when switching types of search
                lastQuery = currentQuery
                search()
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                // Nothing
            }
        })
    }

    override fun onBackPressed() {
        if (isAnimationEnabled) {
            // Show the exit reveal animation (and finish the activity)
            CircularReveal.showExitRevealAnimation(clSearchRoot, { finish() }, fabX, fabY)
        } else {
            finish()
        }
    }

    override fun finish() {
        super.finish()

        if (isAnimationEnabled) {
            // Don't show animations, we'll handle that
            overridePendingTransition(0, 0)
        }
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}