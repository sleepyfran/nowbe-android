package us.nowbe.nowbe.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.profile_basic_info_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.utils.ApiUtils
import us.nowbe.nowbe.utils.CircleTransform

class ProfileBasicInfoView : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.profile_basic_info_view, this, true)
    }

    fun updateInformation(user: User) {
        // Update the user's profile pic
        Glide.with(context)
                .load(ApiUtils.getThumbProfilePicDir(user.profilePicDir))
                .crossFade()
                .transform(CircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.nowbe_logo)
                .into(ivUserPicture)

        // Update the user's online state
        val drawableStatus: Int

        if (user.status!!) {
            drawableStatus = R.drawable.online_state
        } else {
            drawableStatus = R.drawable.offline_state
        }

        ivUserState.setImageDrawable(context.resources.getDrawable(drawableStatus))

        // Show the ONLINE badge if the user is available
        if (user.isOnline != null && user.isOnline!!) {
            tvUserOnline.visibility = View.VISIBLE
        } else {
            tvUserOnline.visibility = View.GONE
        }

        // Update the user's username
        tvUserUsername.text = context.getString(R.string.profile_username, user.username)

        // Update the user's about section
        tvUserAbout.text = user.about

        // Update the user's statistics
        tvUserInfo.text = context.getString(R.string.profile_info, user.age, user.visits, user.friends)
    }
}