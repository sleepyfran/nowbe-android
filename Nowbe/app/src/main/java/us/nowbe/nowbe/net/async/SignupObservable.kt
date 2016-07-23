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
import us.nowbe.nowbe.net.NowbeSignup

class SignupObservable {
    companion object {
        /**
         * Returns an observable that tries to sign up the user into the app
         */
        fun create(user: String, email: String, password: String): Observable<String> {
            return Observable.fromCallable({
                NowbeSignup(user, email, password).attemptSignUp()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}