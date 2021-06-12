package com.fghilmany.moviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fghilmany.moviedb.core.domain.usecase.DataUseCase

class HomeViewModel(private val dataUseCase: DataUseCase): ViewModel() {

    fun getCarousel() = dataUseCase.getCarousel().asLiveData()

    fun getPopularMovie() = dataUseCase.getListPopularMovie().asLiveData()

    fun getComingSoonMovie() = dataUseCase.getListComingSoonMovie().asLiveData()
}