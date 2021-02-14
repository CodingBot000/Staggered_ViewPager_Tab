package com.exam.sample.viewmodel


import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.common.BaseSearchViewModel
import com.exam.sample.common.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.utils.Const
import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SearchViewModel(private val searchRepository: SearchRepository) :  BaseSearchViewModel()  {
    private val textChangeSubject = PublishSubject.create<String>()

    init {
        textChangeSubject.onNext("")
    }

    fun getSearch(keyword:String, offset: Int, isMore : Boolean = false) {
        getSearch(searchRepository, keyword, offset, isMore)
    }

    fun onTextChanged(s: CharSequence, start :Int, before : Int, count: Int){
        if (s.isNullOrEmpty())
            textChangeSubject.onNext("")
        else
            textChangeSubject.onNext(s.toString())
    }

    override fun onCleared() {
        super.onCleared()
    }


    fun getSearchKeyChangeObserver(): Observable<String> {
        return textChangeSubject.debounce(Const.EMIT_INTERVAL, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
    }


}