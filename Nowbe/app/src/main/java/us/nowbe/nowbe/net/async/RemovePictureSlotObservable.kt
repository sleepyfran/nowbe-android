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
import us.nowbe.nowbe.net.NowbeRemovePictureSlot

class RemovePictureSlotObservable {
    companion object {
        /**
         * Returns an observable that removes a comment of the user
         */
        fun create(token: String, commentIndex: Int): Observable<Unit> {
            return Observable.fromCallable({
                NowbeRemovePictureSlot(token, commentIndex).remove()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}