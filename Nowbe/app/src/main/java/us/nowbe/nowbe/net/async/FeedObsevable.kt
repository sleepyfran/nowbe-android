package us.nowbe.nowbe.net.async

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.nowbe.nowbe.model.Feed
import us.nowbe.nowbe.net.NowbeFeedData

class FeedObsevable {
    companion object {
        /**
         * Returns an observable that loads the feed and returns it as a Feed object
         */
        fun create(token: String): Observable<Feed> {
            return Observable.fromCallable({
                NowbeFeedData(token).getFeed()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}