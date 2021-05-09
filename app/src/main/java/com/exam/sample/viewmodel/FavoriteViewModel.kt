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
import com.exam.sample.domain.usecase.UseCaseGetGIFsByIds
import com.exam.sample.utils.Const

import com.exam.sample.utils.Resource
import java.lang.StringBuilder

class FavoriteViewModel(private val useCaseGetGIFsByIds: UseCaseGetGIFsByIds,
                        private val useCaseDbManager: UseCaseDbManager
) : BaseViewModel()  {
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

        showProgress()
        useCaseGetGIFsByIds.setData(sb.toString())
        useCaseGetGIFsByIds.execute(
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

    @SuppressLint("CheckResult")
    fun getFavoriteAll() {
        useCaseDbManager.getFavoriteAll()
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