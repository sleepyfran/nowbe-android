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
import us.nowbe.nowbe.net.NowbeUploadUserAvatar
import java.io.File

class UpdateUserAvatarObservable {
    companion object {
        /**
         * Returns an observable that uploads the avatar of the user
         */
        fun create(token: String, file: File): Observable<Unit> {
            return Observable.fromCallable({
                NowbeUploadUserAvatar(token, file).upload()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}