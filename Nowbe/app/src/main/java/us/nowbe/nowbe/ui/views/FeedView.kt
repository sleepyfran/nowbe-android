package us.nowbe.nowbe.ui.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.feed_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.FeedContent
import us.nowbe.nowbe.utils.ApiUtils

class FeedView(context: Context) : RelativeLayout(context) {
    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.feed_view, this, true)
    }

    fun update(content: FeedContent) {
        // Load the user picture
        Picasso.with(context).load(ApiUtils.getThumbProfilePicDir(content.profilePic, true)).noFade().placeholder(R.drawable.nowbe_logo).into(ivUserPicture)

        // Load the user state (online or not)
        val drawableStatus: Int

        if (content.isOnline) {
            drawableStatus = R.drawable.online_state
        } else {
            drawableStatus = R.drawable.offline_state
        }

        ivUserState.setImageDrawable(context.resources.getDrawable(drawableStatus))

        // Update the full name and the username
        tvUserFullName.text = content.fullname
        tvUsername.text = context.getString(R.string.profile_username, content.username)

        // Update the update text
        tvUpdateText.text = content.update

        // Update the date (timestamp) of the update
        tvDate.text = content.timestamp
    }
}