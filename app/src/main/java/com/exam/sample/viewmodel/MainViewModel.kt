package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.App
import com.exam.sample.R
import com.exam.sample.common.BaseViewModel
import com.exam.sample.livedata.Event
import com.exam.sample.livedata.SingleLiveEvent
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Resource
import com.exam.sample.model.repository.trending.TrendingRepository
import com.exam.sample.utils.isNetworkConnected
import com.exam.sample.utils.toastMsg
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val trendingRepository: TrendingRepository) : BaseViewModel()  {

//    private val _itemLiveData = MutableLiveData<Event<Resource<TrendingData>>>()
//    val itemLiveData: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveData
//    private val _itemLiveDataAdd = MutableLiveData<Event<Resource<TrendingData>>>()
//    val itemLiveDataAdd: LiveData<Event<Resource<TrendingData>>> get() = _itemLiveDataAdd

    // EventObserver를 일부러 사용하지않았음.
    // 샘플을 ViewPager로 보여주려는데 Giphy Api가 Trending data만 제공해서
    // TrendingData로 모든 탭을 구성할수밖에 없음. 그런데 EventObserver를 사용하게되면
    // MainViewModel 을 공유하도록 설정했기때문에
    // 해당 API는 이미 호출된 대상이기때문에 탭마다 복제해서 재호출을 할수가없어서
    // 다른곳에서는 EventObserver를 사용했으나 여기서는 일부러 사용하지않음.
    private val _itemLiveData = MutableLiveData<Resource<TrendingData>>()
    val itemLiveData: LiveData<Resource<TrendingData>> get() = _itemLiveData
    private val _itemLiveDataAdd = MutableLiveData<Resource<TrendingData>>()
    val itemLiveDataAdd: LiveData<Resource<TrendingData>> get() = _itemLiveDataAdd


    interface ServiceListener {
        fun listenerFromService(data : TrendingData)
    }

    var serviceListener : ServiceListener? = null

    fun getTrendingData(offset: Int, rating:String = "", isMore : Boolean = false) {
        if (!isNetworkConnected())
            return

        compositeDisposable.add(
            trendingRepository.requestTrendingData(offset, rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {

                }
                .doAfterTerminate {  }
                .subscribe({ it ->
                    val res = Resource.success(it)
                    if (isMore)
                        _itemLiveDataAdd.postValue(res)
                    else {
                        _itemLiveData.postValue(res)
                        serviceListener?.listenerFromService(it)
                    }

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