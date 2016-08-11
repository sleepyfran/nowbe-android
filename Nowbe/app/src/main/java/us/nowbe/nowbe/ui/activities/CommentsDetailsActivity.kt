package us.nowbe.nowbe.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_comment_details.*
import rx.Subscription
import us.nowbe.nowbe.R
import us.nowbe.nowbe.adapters.CommentsRepliesAdapter
import us.nowbe.nowbe.model.CommentReply
import us.nowbe.nowbe.model.exceptions.RequestNotSuccessfulException
import us.nowbe.nowbe.net.async.CommentRepliesObservable
import us.nowbe.nowbe.net.async.ReplyCommentObservable
import us.nowbe.nowbe.ui.views.CommentsSlotsCommentView
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.ErrorUtils
import us.nowbe.nowbe.utils.Interfaces
import us.nowbe.nowbe.utils.SharedPreferencesUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CommentsDetailsActivity : AppCompatActivity() {

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
     * Adapter of the replies
     */
    lateinit var repliesAdapter: CommentsRepliesAdapter


    fun loadReplies(tokenOwner: String, commentIndex: Int) {
        // Get the token of the user
        val userToken = SharedPreferencesUtils.getToken(this)!!

        previousSubscription = CommentRepliesObservable.create(userToken, tokenOwner, commentIndex - 1).subscribe(
                // On Next
                {
                    replies ->

                    // Load the replies into the adapter
                    repliesAdapter.updateFeedback(replies)

                    // Hide the refreshing icon
                    srlCommentDetails.isRefreshing = false
                },
                // On Error
                {
                    error ->

                    if (error is RequestNotSuccessfulException) {
                        ErrorUtils.showGeneralWhoopsDialog(this)
                    } else {
                        ErrorUtils.showNoConnectionToast(this)
                    }

                    // Hide the refreshing icon
                    srlCommentDetails.isRefreshing = false
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_details)

        // Get the data and the index of the comment from the intent's extras
        val commentAuthorToken = intent.extras?.getString(ApiUtils.KEY_TOKEN)!!
        val commentAuthor = intent.extras?.getString(ApiUtils.KEY_USER)!!
        val commentText = intent.extras?.getString(ApiUtils.KEY_COMMENT_DATA)!!
        val commentIndex = intent.extras?.getInt(ApiUtils.KEY_COMMENT_INDEX)!!

        // Setup the toolbar title
        toolbar.title = getString(R.string.comments_details_title, commentAuthor)

        // Setup the toolbar background and title color
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.accent))
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white))

        // Setup the toolbar back button drawable
        val closeButton = ContextCompat.getDrawable(this, R.drawable.ic_close_white)

        // Set the toolbar as the support action bar and display the close drawable
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(closeButton)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set the data into the activity
        tvCommentText.text = commentText
        ivCommentNumber.setImageDrawable(CommentsSlotsCommentView.indexDrawableFromInt(this, commentIndex))

        // Setup the recycler view
        repliesAdapter = CommentsRepliesAdapter()
        repliesAdapter.onClick = object : Interfaces.OnCommentReplyClick {
            override fun onClick(content: CommentReply) {
                // Show the profile of the person that replied
                val profileIntent = Intent(this@CommentsDetailsActivity, ProfileActivity::class.java)

                // Pass the token of the user as an extra
                profileIntent.putExtra(ApiUtils.KEY_TOKEN, content.token)

                startActivity(profileIntent)
            }
        }
        rvCommentReplies.layoutManager = LinearLayoutManager(this)
        rvCommentReplies.adapter = repliesAdapter

        // Load the replies
        loadReplies(commentAuthorToken, commentIndex)

        // Reload them when the user swipes the comments
        srlCommentDetails.setOnRefreshListener {
            loadReplies(commentAuthorToken, commentIndex)
        }

        // Set the action of the send button
        btnSendCommentReply.setOnClickListener {
            // Get the text that the user entered and check if it's empty or not
            val reply = etSendCommentReply.text.toString()

            if (!TextUtils.isEmpty(reply)) {
                // If it's not, get the token of the user and send it
                val userToken = SharedPreferencesUtils.getToken(this)!!

                previousSubscription = ReplyCommentObservable.create(userToken, commentAuthorToken, reply, commentIndex - 1).subscribe(
                        // On Next
                        {
                            // Force a refresh
                            loadReplies(commentAuthorToken, commentIndex)

                            // Clear the text field
                            etSendCommentReply.text.clear()
                        },
                        // On Error
                        {
                            ErrorUtils.showNoConnectionToast(this)
                        }
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle the back button in the support action bar
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        previousSubscription?.unsubscribe()
        super.onDestroy()
    }
}