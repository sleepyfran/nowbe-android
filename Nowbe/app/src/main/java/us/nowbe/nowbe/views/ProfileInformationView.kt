package us.nowbe.nowbe.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import us.nowbe.nowbe.R

class ProfileInformationView : RelativeLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.profile_information_view, this, true)
    }

    // TODO: Expose an update method that will update the views according to a representation of an user
}