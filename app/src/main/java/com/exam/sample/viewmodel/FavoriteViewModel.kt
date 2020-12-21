package com.exam.sample.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.sample.common.BaseViewModel
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.TrendingData

import com.exam.sample.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.StringBuilder

class FavoriteViewModel(private val favoriteInfoRepository: FavoriteInfoRepository) : BaseViewModel()  {

    private val _itemLiveData = MutableLiveData<Resource<TrendingData>>()
    val itemLiveData: LiveData<Resource<TrendingData>> get() = _itemLiveData

    fun getFavoriteInfoRequest(list : List<FavoriteInfo>) {
        if (list.isEmpty())
            return

        var sb = StringBuilder()
        for (info in list) {
            sb.append("${info.userId},")
        }
        sb.deleteCharAt(sb.lastIndex)

        compositeDisposable.add(
            favoriteInfoRepository.requestGIFsByIds(sb.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showProgress()
                }
                .doAfterTerminate { hideProgress() }
                .subscribe({ it ->
                    _itemLiveData.postValue(Resource.success(it))
                }, {
                     _itemLiveData.postValue(Resource.error(it.message.toString(), null))
                })
        )
    }

    fun getFavoriteAll() : LiveData<List<FavoriteInfo>> {
        return favoriteInfoRepository.getFavoriteAllDB()
    }

    override fun onCleared() {
        super.onCleared()

    }
}