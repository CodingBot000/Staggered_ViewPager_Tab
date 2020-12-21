package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.common.BaseViewModel
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchViewModel(private val searchRepository: SearchRepository) : BaseViewModel()  {

    private val _itemLiveData = MutableLiveData<Resource<TrendingData>>()
    val itemLiveData: LiveData<Resource<TrendingData>> get() = _itemLiveData
    private val _itemLiveDataAdd = MutableLiveData<Resource<TrendingData>>()
    val itemLiveDataAdd: LiveData<Resource<TrendingData>> get() = _itemLiveDataAdd

    fun getSearch(keyword:String, offset: Int, isMore : Boolean = false) {
        compositeDisposable.add(
            searchRepository.requestSearchData(keyword, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showProgress()
                }
                .doAfterTerminate { hideProgress() }
                .subscribe({ it ->
                    val res = Resource.success(it)
                    if (isMore)
                        _itemLiveDataAdd.postValue(res)
                    else
                        _itemLiveData.postValue(res)

                }, {
                    val res = Resource.error(it.message.toString(), null)
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