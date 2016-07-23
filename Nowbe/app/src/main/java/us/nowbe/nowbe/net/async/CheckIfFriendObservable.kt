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
import us.nowbe.nowbe.net.NowbeCheckIfFriend

class CheckIfFriendObservable {
    companion object {
        /**
         * Returns an observable that returns the relationship between two users
         */
        fun create(userToken: String, friendToken: String): Observable<String> {
            return Observable.fromCallable({
                NowbeCheckIfFriend(userToken, friendToken).check()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}