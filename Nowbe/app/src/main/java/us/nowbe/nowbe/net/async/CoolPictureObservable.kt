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
import us.nowbe.nowbe.net.NowbeCoolPicture

class CoolPictureObservable {
    companion object {
        /**
         * Returns an observable that cools a picture
         */
        fun create(userToken: String, profileToken: String, pictureIndex: Int): Observable<Unit> {
            return Observable.fromCallable({
                NowbeCoolPicture(userToken, profileToken, pictureIndex).cool()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}