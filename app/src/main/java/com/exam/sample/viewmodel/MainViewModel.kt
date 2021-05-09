package com.exam.sample.viewmodel


import com.exam.sample.viewmodel.base.BaseViewModel
import com.exam.sample.model.data.TrendingData
import com.exam.sample.domain.usecase.UseCaseGetTrendingData

import com.exam.sample.utils.isNetworkConnected


class MainViewModel(private val useCaseGetTrendingData: UseCaseGetTrendingData) : BaseViewModel()  {

    interface ServiceListener {
        fun listenerFromService(data : TrendingData)
    }

    var serviceListener : ServiceListener? = null

    fun getDataFromBackground(offset: Int, rating:String = "", isMore : Boolean = false) {
        if (!isNetworkConnected())
            return

        useCaseGetTrendingData.setData(offset, rating)
        useCaseGetTrendingData.execute(
            onSuccess = {
                serviceListener?.listenerFromService(it)
            },
            onError = {

            },
            onFinished = {

            }
        )
    }

    override fun onCleared() {
        super.onCleared()
    }

}