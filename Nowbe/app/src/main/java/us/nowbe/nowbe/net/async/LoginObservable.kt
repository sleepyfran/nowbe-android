package us.nowbe.nowbe.net.async

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.nowbe.nowbe.net.NowbeLogin
import us.nowbe.nowbe.net.NowbeResponse

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class LoginObservable {
    companion object {
        /**
         * TODO: Check
         */
        fun create(username: String, password: String): Observable<NowbeResponse<String>> {
            return Observable.fromCallable({
                NowbeLogin(username, password).attemptLogin()
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        }
    }
}