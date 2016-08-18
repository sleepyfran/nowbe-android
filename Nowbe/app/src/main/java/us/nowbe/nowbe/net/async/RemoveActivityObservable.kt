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
import us.nowbe.nowbe.net.NowbeRemoveActivity

class RemoveActivityObservable {
    companion object {
        /**
         * Returns an observable that removes the activity of the token and id specified
         */
        fun create(token: String, id: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeRemoveActivity(token, id).remove()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}