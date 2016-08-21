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
import us.nowbe.nowbe.net.NowbeRemoveUser

class RemoveUserObservable {
    companion object {
        /**
         * Returns an observable that removes an user
         */
        fun create(tokenOfUser: String, tokenToRemove: String): Observable<String> {
            return Observable.fromCallable({
                NowbeRemoveUser(tokenOfUser, tokenToRemove).remove()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}