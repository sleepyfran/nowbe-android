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
import us.nowbe.nowbe.net.NowbeNotifyAccessFromSearch

class NotifyAccessFromSearchObservable {
    companion object {
        /**
         * Returns an observable that notifies an access from the search
         */
        fun create(visitantToken: String, visitantNickname: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeNotifyAccessFromSearch(visitantToken, visitantNickname).notifyAccess()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}