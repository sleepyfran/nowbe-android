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
import us.nowbe.nowbe.net.NowbeReplyComment

class ReplyCommentObservable {
    companion object {
        /**
         * Returns an observable that replies to a comment
         */
        fun create(token: String, tokenOwner: String, reply: String, commentNo: Int): Observable<Unit> {
            return Observable.fromCallable({
                NowbeReplyComment(token, tokenOwner, reply, commentNo).reply()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}