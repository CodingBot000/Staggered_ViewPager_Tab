package com.exam.sample.viewmodel


import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.viewmodel.base.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.DBResultData
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData
import com.exam.sample.domain.usecase.UseCaseDbManager
import com.exam.sample.domain.usecase.UseCaseGetDetailData

import com.exam.sample.utils.Const

import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected


class DetailViewModel(private val useCaseDbManager: UseCaseDbManager,
                      private val useCaseGetDetailData: UseCaseGetDetailData
): BaseViewModel()  {
    private val _dbDataSuccessEvent = MutableLiveData<Event<Resource<DBResultData>>>()
    val dbDataSuccessEvent: LiveData<Event<Resource<DBResultData>>> get() = _dbDataSuccessEvent
    private val _favoriteCheckEvent = MutableLiveData<Event<Boolean>>()
    val favoriteCheckEvent: LiveData<Event<Boolean>> get() = _favoriteCheckEvent
    private val _btnSimpleEvent = MutableLiveData<Event<Int>>()
    val btnSimpleEvent: LiveData<Event<Int>> get() = _btnSimpleEvent

    private val _itemLiveData = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveData: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveData


    fun getDetailData(id: String) {
        if (!isNetworkConnected())
            return

        showProgress()
        useCaseGetDetailData.setData(id)
        useCaseGetDetailData.execute(
            onSuccess = {
                _itemLiveData.postValue(Event(Resource.success(it)))
            },
            onError = {
                _itemLiveData.postValue(Event(Resource.error(it.message.toString(), null)))
            },
            onFinished = {
                hideProgress()
            }
        )
    }


    fun insertFavorite(favoriteInfo: FavoriteInfo)  {
        compositeDisposable.add(
            useCaseDbManager.insertFavorites(favoriteInfo)
                .subscribe({
                    _dbDataSuccessEvent.postValue(Event(Resource.success(DBResultData(Const.DB_INSERT, null,true))))
                }, {
                    _dbDataSuccessEvent.postValue(Event(Resource.error(it.message.toString(), DBResultData(Const.DB_INSERT, null,false))))
                })
        )
    }

    fun removeFavorite(favoriteInfo: FavoriteInfo) {
        compositeDisposable.add(
            useCaseDbManager.removeFavorite(favoriteInfo)
                .subscribe({
                    _dbDataSuccessEvent.postValue(Event(Resource.success(DBResultData(Const.DB_DELETE, null,true))))
                }, {
                    _dbDataSuccessEvent.postValue(Event(Resource.error(it.message.toString(), DBResultData(Const.DB_DELETE, null,false))))
                })
        )
    }

    @SuppressLint("CheckResult")
    fun getFavorite(userId : String) {
        useCaseDbManager.getFavorite(userId)
            .subscribe ({
                _dbDataSuccessEvent.postValue(Event(Resource.success(DBResultData(Const.DB_SELECT, it, true))))
            }, {
                _dbDataSuccessEvent.postValue(Event(Resource.error(it.message.toString(), DBResultData(Const.DB_SELECT, it,false))))
            })
    }

    fun checkBoxChecked(b: Boolean) {
        _favoriteCheckEvent.value = Event(b)

    }

    fun btnClickEventSend(index:Int) {
        _btnSimpleEvent.value = Event(index)

    }
    override fun onCleared() {
        super.onCleared()
    }

}