package com.exam.sample.viewmodel


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.common.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.DBResultData
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Const

import com.exam.sample.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class FavoriteViewModel(private val favoriteInfoRepository: FavoriteInfoRepository) : BaseViewModel()  {
    private val _dbDataSuccessEvent = MutableLiveData<Event<Resource<DBResultData>>>()
    val dbDataSuccessEvent: LiveData<Event<Resource<DBResultData>>> get() = _dbDataSuccessEvent

    private val _itemLiveData = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveData: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveData

    fun getFavoriteInfoRequest(list : List<FavoriteInfo>) {
        if (list.isEmpty())
            return

        var sb = StringBuilder()
        for (info in list) {
            sb.append("${info.userId},")
        }
        sb.deleteCharAt(sb.lastIndex)

        compositeDisposable.add(
            favoriteInfoRepository.requestGIFsByIds(sb.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showProgress()
                }
                .doAfterTerminate { hideProgress() }
                .subscribe({ it ->
                    _itemLiveData.postValue(Event(Resource.success(it)))
                }, {
                     _itemLiveData.postValue(Event(Resource.error(it.message.toString(), null)))
                })
        )
    }

    @SuppressLint("CheckResult")
    fun getFavoriteAll() {
        favoriteInfoRepository.getFavoriteAllDB().subscribeOn (Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            _dbDataSuccessEvent.postValue(Event(Resource.success(DBResultData(Const.DB_SELECT, it!!, true))))
        }, {
            _dbDataSuccessEvent.postValue(Event(Resource.error(it.message.toString(), DBResultData(
                Const.DB_SELECT, null,false)
            )))
        })
    }

    override fun onCleared() {
        super.onCleared()

    }
}