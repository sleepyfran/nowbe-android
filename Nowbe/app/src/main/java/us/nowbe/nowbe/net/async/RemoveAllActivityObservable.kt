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
import us.nowbe.nowbe.net.NowbeRemoveAllActivity

class RemoveAllActivityObservable {
    companion object {
        /**
         * Returns an observable that removes all the activity from the specified token
         */
        fun create(token: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeRemoveAllActivity(token).removeAll()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}