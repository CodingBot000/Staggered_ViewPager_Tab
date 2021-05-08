package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.App
import com.exam.sample.R
import com.exam.sample.common.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.livedata.SingleLiveEvent
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.utils.Resource
import com.exam.sample.model.repository.trending.TrendingRepository
import com.exam.sample.model.usecase.UseCaseApiManager
import com.exam.sample.utils.isNetworkConnected
import com.exam.sample.utils.toastMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val useCaseApiManager: UseCaseApiManager) : BaseViewModel()  {

    interface ServiceListener {
        fun listenerFromService(data : TrendingData)
    }

    var serviceListener : ServiceListener? = null

    fun getDataFromBackground(offset: Int, rating:String = "", isMore : Boolean = false) {
        if (!isNetworkConnected())
            return

        compositeDisposable.add(
            useCaseApiManager.requestTrendingData(offset, rating)
                .subscribe({ it ->
                    serviceListener?.listenerFromService(it)

                }, {

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
    }

}