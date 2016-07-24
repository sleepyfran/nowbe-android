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
import us.nowbe.nowbe.net.NowbeSayHello

class SayHelloObservable {
    companion object {
        /**
         * Returns an observable that says hello to an user
         */
        fun create(tokenFrom: String, tokenTo: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeSayHello(tokenFrom, tokenTo).sayHello()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}