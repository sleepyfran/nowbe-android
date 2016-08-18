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
import us.nowbe.nowbe.net.NowbeUpdateUserInterests

class UpdateUserInterestsObservable {
    companion object {
        /**
         * Returns an observable that attempts to change the interests of the user
         */
        fun create(token: String, interests: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeUpdateUserInterests(token, interests).update()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}