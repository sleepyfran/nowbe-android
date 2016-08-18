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
import us.nowbe.nowbe.model.Activity
import us.nowbe.nowbe.net.NowbeActivityData

class ActivityDataObservable {
    companion object {
        /**
         * Returns an observable that gets the activity of the provided token
         */
        fun create(token: String): Observable<Activity> {
            return Observable.fromCallable({
                NowbeActivityData(token).getActivity()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}