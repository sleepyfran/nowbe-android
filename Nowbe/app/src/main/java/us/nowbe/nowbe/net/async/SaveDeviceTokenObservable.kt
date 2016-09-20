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
import us.nowbe.nowbe.net.NowbeSaveDeviceToken

class SaveDeviceTokenObservable {
    companion object {
        /**
         * Returns an observable that saves the device token of the user
         */
        fun create(userToken: String, deviceToken: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeSaveDeviceToken(userToken, deviceToken).save()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}