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
import us.nowbe.nowbe.net.NowbeChangeStatus

class ChangeStatusObservable {
    companion object {
        /**
         * Returns an observable that changes the status of the user
         */
        fun create(token: String, isAvailable: Boolean): Observable<Unit> {
            return Observable.fromCallable({
                NowbeChangeStatus(token, isAvailable).changeStatus()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}