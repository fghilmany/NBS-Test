package com.fghilmany.moviedb.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fghilmany.moviedb.core.domain.usecase.DataUseCase

class PopularViewModel(private val dataUseCase: DataUseCase): ViewModel() {

    fun getPopularMovie() = dataUseCase.getListPopularMovie().asLiveData()

}