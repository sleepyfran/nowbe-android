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
import us.nowbe.nowbe.net.NowbeUpdateUserVisibleName

class UpdateUserVisibleNameObservable {
    companion object {
        /**
         * Returns an observable that changes the visible (mutable) name of the user
         */
        fun create(newVisibleName: String, token: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeUpdateUserVisibleName(newVisibleName, token).updateVisibleName()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}