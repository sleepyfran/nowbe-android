package us.nowbe.nowbe.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import us.nowbe.nowbe.adapters.holders.FeedHolder
import us.nowbe.nowbe.views.FeedView

class FeedAdapter : RecyclerView.Adapter<FeedHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        return FeedHolder(FeedView(parent.context))
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        // TODO: Update the object dynamically
    }

    override fun getItemCount(): Int {
        return 20
    }
}
