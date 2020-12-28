package com.exam.sample.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.common.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.data.DBResultData
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Const

import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DetailViewModel(private val detailDataRepository: DetailDataRepository) : BaseViewModel()  {
    private val _dbDataSuccessEvent = MutableLiveData<Event<Resource<DBResultData>>>()
    val dbDataSuccessEvent: LiveData<Event<Resource<DBResultData>>> get() = _dbDataSuccessEvent

    private val _itemLiveData = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveData: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveData

    fun getDetailData(id : String) {
        if (!isNetworkConnected())
            return

        compositeDisposable.add(
            detailDataRepository.requestDetailData(id)
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

    fun insertFavorite(favoriteInfo: FavoriteInfo)  {
        compositeDisposable.add(
            detailDataRepository.insertFavoriteDB(favoriteInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {

                }
                .doAfterTerminate {  }
                .subscribe({
                    _dbDataSuccessEvent.postValue(Event(Resource.success(DBResultData(Const.DB_INSERT, true))))
                }, {
                    _dbDataSuccessEvent.postValue(Event(Resource.error(it.message.toString(), DBResultData(Const.DB_INSERT, false))))
                })
        )
    }

    fun removeFavorite(favoriteInfo: FavoriteInfo) {
        compositeDisposable.add(
            detailDataRepository.removeFavoriteDB(favoriteInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {

                }
                .doAfterTerminate {  }
                .subscribe({
                    _dbDataSuccessEvent.postValue(Event(Resource.success(DBResultData(Const.DB_DELETE, true))))
                }, {
                    _dbDataSuccessEvent.postValue(Event(Resource.error(it.message.toString(), DBResultData(Const.DB_DELETE, false))))
                })
        )
    }

    fun getFavorite(userId : String) : LiveData<FavoriteInfo> {
        return detailDataRepository.getFavoriteDB(userId)
    }

    override fun onCleared() {
        super.onCleared()
    }

}