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
import us.nowbe.nowbe.model.CommentReply
import us.nowbe.nowbe.net.NowbeDeleteReply

class DeleteReplyObservable {
    companion object {
        /**
         * Returns an observable that deletes the reply of an user
         */
        fun create(tokenOwner: String, reply: CommentReply): Observable<Unit> {
            return Observable.fromCallable({
                NowbeDeleteReply(tokenOwner, reply).remove()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}