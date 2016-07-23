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

class AddUserObservable {
    companion object {
        /**
         * Returns an observable that adds an user as a friend to the app user's account
         */
        fun create(tokenOfUser: String, tokenToAdd: String): Observable<String> {
            return Observable.fromCallable({
                NowbeAddUser(tokenOfUser, tokenToAdd).add()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}