package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.common.BaseSearchViewModel
import com.exam.sample.common.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.utils.Resource
import com.exam.sample.utils.isNetworkConnected
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchViewModel(private val searchRepository: SearchRepository) :  BaseSearchViewModel()  {

    fun getSearch(keyword:String, offset: Int, isMore : Boolean = false) {
        getSearch(searchRepository, keyword, offset, isMore)
    }

    override fun onCleared() {
        super.onCleared()
    }

}