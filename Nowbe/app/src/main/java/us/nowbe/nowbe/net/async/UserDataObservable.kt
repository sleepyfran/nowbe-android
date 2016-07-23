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
import us.nowbe.nowbe.model.User
import us.nowbe.nowbe.net.NowbeUserData

class UserDataObservable {
    companion object {
        /**
         * Returns an observable that loads an user and returns it
         */
        fun create(token: String): Observable<User> {
            return Observable.fromCallable({
                NowbeUserData(token).getUser()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}