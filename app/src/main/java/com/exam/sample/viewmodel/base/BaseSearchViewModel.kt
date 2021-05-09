package com.exam.sample.common


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.domain.usecase.UseCaseApiManager
import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected

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