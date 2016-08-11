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
import us.nowbe.nowbe.net.NowbeCommentReplies

class CommentRepliesObservable {
    companion object {
        /**
         * Returns an observable that loads the replies of a comment
         */
        fun create(token: String, tokenOwner: String, commentNo: Int): Observable<MutableList<CommentReply>> {
            return Observable.fromCallable({
                NowbeCommentReplies(token, tokenOwner, commentNo).getReplies()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}