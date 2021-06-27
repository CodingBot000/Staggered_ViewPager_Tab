package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.domain.usecase.UseCaseGetSearchData
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected
import com.exam.sample.viewmodel.base.BaseSearchViewModel
import com.exam.sample.viewmodel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(private val useCaseGetSearchData: UseCaseGetSearchData) : BaseViewModel()  {
    private val _itemLiveData = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveData: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveData
    private val _itemLiveDataAdd = MutableLiveData<Event<Resource<TrendingData>>>()
    val itemLiveDataAdd: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveDataAdd

    fun getSearch(keyword:String, offset: Int, isMore : Boolean = false) {
        getSearch(useCaseGetSearchData, keyword, offset, isMore)
    }

    private fun getSearch(useCaseGetSearchData: UseCaseGetSearchData, keyword:String, offset: Int, isMore : Boolean = false) {
        if (!isNetworkConnected())
            return

        showProgress()
        useCaseGetSearchData.setData(keyword, offset)
        useCaseGetSearchData.execute(
            onSuccess = {
                val res = Event(Resource.success(it))
                if (isMore)
                    _itemLiveDataAdd.postValue(res)
                else
                    _itemLiveData.postValue(res)
            },
            onError = {
                val res = Event(Resource.error(it.message.toString(), null))
                if (isMore)
                    _itemLiveDataAdd.postValue(res)
                else
                    _itemLiveData.postValue(res)
            },
            onFinished = {
                hideProgress()
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}
