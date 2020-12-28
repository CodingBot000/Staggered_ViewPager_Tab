package com.exam.sample.di


import android.widget.SearchView
import com.exam.sample.viewmodel.*
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { TrendingViewModel(get()) }
    viewModel { ArtistsViewModel(get()) }
    viewModel { ClipsViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }

}

