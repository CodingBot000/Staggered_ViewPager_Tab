package com.exam.sample.domain.usecase

import com.exam.sample.domain.usecase.base.SingleUseCase
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.repository.favorite.FavoriteInfoRepository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCaseDbInsert (private val favoriteInfoRepository: FavoriteInfoRepository)
: SingleUseCase<Long>() {

    private lateinit var favoriteInfo: FavoriteInfo

    fun setData(favoriteInfo: FavoriteInfo) {
        this.favoriteInfo = favoriteInfo
    }

    override fun buildUseCaseSingle(): Single<Long> {
        return favoriteInfoRepository.insertFavoriteDB(favoriteInfo)

    }
}