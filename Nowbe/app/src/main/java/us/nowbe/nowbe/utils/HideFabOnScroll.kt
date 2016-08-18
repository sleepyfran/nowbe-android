package us.nowbe.nowbe.utils

import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class HideFabOnScroll(val fab: FloatingActionButton) : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            fab.hide()
        } else if (dy < 0) {
            fab.show()
        }
    }
}