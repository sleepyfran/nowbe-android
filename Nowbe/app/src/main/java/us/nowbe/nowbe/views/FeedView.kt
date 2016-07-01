package us.nowbe.nowbe.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.RelativeLayout
import us.nowbe.nowbe.R

class FeedView(context: Context) : RelativeLayout(context) {
    init {
        // Inflate the view
        LayoutInflater.from(context).inflate(R.layout.list_item_update, this, true)
    }
}