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
import us.nowbe.nowbe.net.NowbeRemoveCouple

class RemoveCoupleObservable {
    companion object {
        /**
         * Returns an observable that removes the couple of the user
         */
        fun create(userToken: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeRemoveCouple(userToken).removeCouple()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}