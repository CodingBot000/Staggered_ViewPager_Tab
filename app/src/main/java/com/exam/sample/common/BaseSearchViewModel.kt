package com.exam.sample.common


import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.sample.App
import com.exam.sample.R
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.model.usecase.UseCaseApiManager
import com.exam.sample.utils.Const
import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected
import com.exam.sample.utils.toastMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseSearchViewModel(): BaseViewModel() {
    private val _itemLiveData = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveData: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveData
    private val _itemLiveDataAdd = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveDataAdd: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveDataAdd

    fun getSearch(useCaseApiManager: UseCaseApiManager,  keyword:String, offset: Int, isMore : Boolean = false) {
        if (!isNetworkConnected())
            return

        compositeDisposable.add(
            useCaseApiManager.requestSearchData(keyword, offset)
                .doOnSubscribe {
                    showProgress()
                }
                .doAfterTerminate { hideProgress() }
                .subscribe({ it ->
                    val res = Event(Resource.success(it))
                    if (isMore)
                        _itemLiveDataAdd.postValue(res)
                    else
                        _itemLiveData.postValue(res)

                }, {
                    val res = Event(Resource.error(it.message.toString(), null))
                    if (isMore)
                        _itemLiveDataAdd.postValue(res)
                    else
                        _itemLiveData.postValue(res)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
    }

}