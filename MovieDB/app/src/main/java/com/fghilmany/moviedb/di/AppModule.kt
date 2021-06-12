package com.fghilmany.moviedb.di

import com.fghilmany.moviedb.core.domain.usecase.DataInteractor
import com.fghilmany.moviedb.core.domain.usecase.DataUseCase
import com.fghilmany.moviedb.ui.detail.DetailViewModel
import com.fghilmany.moviedb.ui.favorite.FavoriteViewModel
import com.fghilmany.moviedb.ui.home.HomeViewModel
import com.fghilmany.moviedb.ui.popular.PopularViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<DataUseCase> { DataInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { PopularViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}