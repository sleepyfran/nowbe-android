package us.nowbe.nowbe.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.profile_basic_info_view.*
import kotlinx.android.synthetic.main.profile_basic_info_view.view.*
import us.nowbe.nowbe.R
import us.nowbe.nowbe.model.User

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
        Picasso.with(context).load(user.profilePicDir).noFade().into(ivUserPicture)

        // Update the user's online state
        val drawableStatus: Int

        if (user.status!!) {
            drawableStatus = R.drawable.online_state
        } else {
            drawableStatus = R.drawable.offline_state
        }

        ivUserState.setImageDrawable(context.resources.getDrawable(drawableStatus))

        // Update the user's username
        tvUserUsername.text = context.getString(R.string.profile_username, user.username)

        // Update the user's about section
        tvUserAbout.text = user.about

        // Update the user's statistics
        tvUserInfo.text = context.getString(R.string.profile_info, user.age, user.visits, user.friends)
    }
}