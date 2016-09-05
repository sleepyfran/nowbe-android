package us.nowbe.nowbe.net.async

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

import org.json.JSONArray
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import us.nowbe.nowbe.net.NowbeGetCoolers

class GetCoolersObservable {
    companion object {
        /**
         * Returns an observable that gets the coolers of an image
         */
        fun create(token: String, imgId: String): Observable<JSONArray> {
            return Observable.fromCallable({
                NowbeGetCoolers(token, imgId).getCoolers()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}