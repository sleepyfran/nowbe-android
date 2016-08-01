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
import us.nowbe.nowbe.net.NowbeUpdateUserBirthday

class UpdateUserBirthdayObservable {
    companion object {
        /**
         * Returns an observable that updates the birthday of the user
         */
        fun create(token: String, date: String): Observable<Boolean> {
            return Observable.fromCallable({
                NowbeUpdateUserBirthday(token, date).update()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}