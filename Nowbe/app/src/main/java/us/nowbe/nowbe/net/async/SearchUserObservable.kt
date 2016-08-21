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
import us.nowbe.nowbe.model.SearchResult
import us.nowbe.nowbe.net.NowbeAddUser
import us.nowbe.nowbe.net.NowbeSearchUser

class SearchUserObservable {
    companion object {
        /**
         * Returns an observable that searches for an user with the specified query
         */
        fun create(type: NowbeSearchUser.Companion.Type, token: String, query: String?): Observable<MutableList<SearchResult>> {
            return Observable.fromCallable({
                NowbeSearchUser(type, token, query).search()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}