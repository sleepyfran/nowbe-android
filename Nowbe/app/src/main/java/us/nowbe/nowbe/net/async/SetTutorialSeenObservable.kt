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
import us.nowbe.nowbe.net.NowbeSetTutorialSeen

class SetTutorialSeenObservable {
    companion object {
        /**
         * Returns an observable that sets the tutorial as seen
         */
        fun create(userToken: String): Observable<Unit> {
            return Observable.fromCallable({
                NowbeSetTutorialSeen(userToken).setTutorial()
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}