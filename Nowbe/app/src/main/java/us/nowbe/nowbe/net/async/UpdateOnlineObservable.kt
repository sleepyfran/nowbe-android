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
import us.nowbe.nowbe.net.NowbeAddUser
import us.nowbe.nowbe.net.NowbeUpdateOnline

class UpdateOnlineObservable {
    companion object {
        /**
         * Returns an observable that updates the online state of the user
         */
        fun create(token: String, state: Int): Observable<Unit> {
            return Observable.fromCallable({
                NowbeUpdateOnline(token, state).updateOnline()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}