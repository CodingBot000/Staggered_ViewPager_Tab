package com.exam.sample.domain.usecase


import com.exam.sample.common.BaseUseCase
import com.exam.sample.livedata.Event
import com.exam.sample.model.data.TrendingData
import com.exam.sample.model.repository.detail.DetailDataRepository
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository
import com.exam.sample.model.repository.search.SearchRepository
import com.exam.sample.model.repository.trending.TrendingRepository
import com.exam.sample.utils.Resource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCaseApiManager (private val detailDataRepository: DetailDataRepository ,
                         private val trendingRepository: TrendingRepository,
                         private val favoriteInfoRepository: FavoriteInfoRepository,
                         private val searchRepository: SearchRepository
) : BaseUseCase<Unit, TrendingData>() {

    fun requestDetailData(id: String): Single<TrendingData> {
        return detailDataRepository.requestDetailData(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestTrendingData(offset: Int, rating: String): Single<TrendingData> {
        return trendingRepository.requestTrendingData(offset, rating)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestGIFsByIds(idsList: String): Single<TrendingData> {
        return favoriteInfoRepository.requestGIFsByIds(idsList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun requestSearchData(keyword: String, offset: Int): Single<TrendingData> {
        return searchRepository.requestSearchData(keyword, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun execute(parameter: Unit): Single<Event<Resource<TrendingData>>> {
        return detailDataRepository.requestDetailData(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}