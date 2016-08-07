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
import us.nowbe.nowbe.net.NowbeUpdateUserComment

class UpdateUserCommentObservable {
    companion object {
        /**
         * Returns an observable that updates the comment of an user
         */
        fun create(token: String, commentIndex: Int, comment: String): Observable<Boolean> {
            return Observable.fromCallable({
                NowbeUpdateUserComment(token, commentIndex, comment).updateComment()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}