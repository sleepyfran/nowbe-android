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
import us.nowbe.nowbe.net.NowbeChangeProfileVisibility

class ChangeProfileVisibilityObservable {
    companion object {
        /**
         * Returns an observable that changes the visibility of the user's profile
         */
        fun create(token: String, status: Int): Observable<Unit> {
            return Observable.fromCallable({
                NowbeChangeProfileVisibility(token, status).change()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}