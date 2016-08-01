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
import us.nowbe.nowbe.net.NowbeUpdateUserAbout

class UpdateUserAboutObservable {
    companion object {
        /**
         * Returns an observable that changes the about section of the user
         */
        fun create(token: String, about: String): Observable<Boolean> {
            return Observable.fromCallable({
                NowbeUpdateUserAbout(token, about).updateAbout()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}