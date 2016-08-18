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
import us.nowbe.nowbe.net.NowbeUploadUserSlotPicture
import java.io.File

class UpdateUserSlotObservable {
    companion object {
        /**
         * Returns an observable that updates a slot of the user
         */
        fun create(token: String, file: File, slotIndex: Int): Observable<Unit> {
            return Observable.fromCallable({
                NowbeUploadUserSlotPicture(token, file, slotIndex).upload()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}