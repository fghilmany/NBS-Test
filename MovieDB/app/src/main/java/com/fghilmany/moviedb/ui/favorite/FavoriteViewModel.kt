package com.fghilmany.moviedb.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import com.fghilmany.moviedb.core.domain.usecase.DataUseCase

class FavoriteViewModel(private val dataUseCase: DataUseCase): ViewModel() {

    private val _moviePopular = MutableLiveData<PopularMovie>()
    private val _movieComingSoon = MutableLiveData<ComingSoonMovie>()

    fun getFavorite() = dataUseCase.getFavoriteMovie().asLiveData()

    fun setMovie(movie: FavoriteMovie){
        _moviePopular.value = with(movie){
            PopularMovie(id?:0, overview?:"", originalTitle?:"", title?:"", posterPath?:"", backdropPath?:"", false)
        }

        _movieComingSoon.value = with(movie){
            ComingSoonMovie(id?:0, overview?:"", originalTitle?:"", title?:"", posterPath?:"", backdropPath?:"", false)
        }
    }

    fun setFavoritePopular() {
        _moviePopular.value?.let { movie ->
            dataUseCase.setPopularFavorite(movie, false)
        }
    }

    fun setFavoriteComingSoon() {
        _movieComingSoon.value?.let { movie ->
            dataUseCase.setComingSoonFavorite(movie, false)
        }
    }

}