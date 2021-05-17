package com.exam.sample.domain.usecase

import com.exam.sample.domain.usecase.base.SingleUseCase
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCaseDbSelectAll (private val favoriteInfoRepository: FavoriteInfoRepository)
: SingleUseCase<List<FavoriteInfo>>() {

    override fun buildUseCaseSingle(): Single<List<FavoriteInfo>> {
        return favoriteInfoRepository.getFavoriteAllDB()
    }
}