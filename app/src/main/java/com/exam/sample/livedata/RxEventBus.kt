package com.exam.sample.livedata

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

object RxEventBus {

    private val mSubject = PublishSubject.create<String>()

    fun sendEvent(str: String) {
        mSubject.onNext(str)
    }

    fun getObservable() : Observable<String> {
        return mSubject
    }
}