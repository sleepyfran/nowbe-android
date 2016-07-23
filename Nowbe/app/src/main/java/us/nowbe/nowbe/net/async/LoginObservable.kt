package us.nowbe.nowbe.net.async

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.nowbe.nowbe.net.NowbeLogin

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class LoginObservable {
    companion object {
        /**
         * Returns an observable that logs the user into the web and returns the response
         */
        fun create(username: String, password: String): Observable<String> {
            return Observable.fromCallable({
                NowbeLogin(username, password).attemptLogin()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}