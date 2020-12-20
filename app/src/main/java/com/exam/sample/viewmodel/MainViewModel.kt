package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Resource
import com.exam.sample.model.repository.trending.TrendingRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val trendingRepository: TrendingRepository) : BaseViewModel()  {

    private val _itemLiveData = MutableLiveData<Resource<TrendingData>>()
    val itemLiveData: LiveData<Resource<TrendingData>> get() = _itemLiveData
    private val _itemLiveDataAdd = MutableLiveData<Resource<TrendingData>>()
    val itemLiveDataAdd: LiveData<Resource<TrendingData>> get() = _itemLiveDataAdd

    fun getTrendingData(offset: Int, isMore : Boolean = false) {
        compositeDisposable.add(
            trendingRepository.requestTrendingData(offset)
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