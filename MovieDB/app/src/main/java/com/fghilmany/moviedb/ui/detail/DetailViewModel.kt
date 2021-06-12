package com.fghilmany.moviedb.ui.detail

import androidx.lifecycle.*
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import com.fghilmany.moviedb.core.domain.usecase.DataUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(private val dataUseCase: DataUseCase): ViewModel() {

    private val _idMovie = MutableLiveData<String>()
    private val _isFavorite = MutableLiveData<Boolean>()
    private val _isPopular = MutableLiveData<Boolean>()
    private val _movie = MutableLiveData<FavoriteMovie>()
    private val _moviePopular = MutableLiveData<PopularMovie>()
    private val _movieComingSoon = MutableLiveData<ComingSoonMovie>()

    fun setId(idMovie: String?){
        if (idMovie == _idMovie.value) return
        _idMovie.value = idMovie
    }

    fun setFavorite(isFavorite: Boolean?){
        if (isFavorite == _isFavorite.value) return
        _isFavorite.value = isFavorite
    }

    fun setMovieFavorite(movie: DetailMovieResponse){
        _movie.value = with(movie){

            FavoriteMovie(id?:0, overview?:"", originalTitle?:"", title?:"", posterPath?:"", backdropPath?:"", false)

        }

        _moviePopular.value = with(movie){
            PopularMovie(id?:0, overview?:"", originalTitle?:"", title?:"", posterPath?:"", backdropPath?:"", false)
        }

        _movieComingSoon.value = with(movie){
            ComingSoonMovie(id?:0, overview?:"", originalTitle?:"", title?:"", posterPath?:"", backdropPath?:"", false)
        }
    }

    fun setFavoritePopular() {
        _moviePopular.value?.let { movie ->
            _isFavorite.value?.let {
                    favorite -> dataUseCase.setPopularFavorite(movie, favorite)
            }
        }
    }

    fun setFavoriteComingSoon() {
        _movieComingSoon.value?.let { movie ->
            _isFavorite.value?.let {
                    favorite -> dataUseCase.setComingSoonFavorite(movie, favorite)
            }
        }
    }

    fun getDetail() = _idMovie.value?.let { dataUseCase.getDetail(it).asLiveData() }

    fun setFavorite() = GlobalScope.launch {
        _movie.value?.let { movie ->
            _isFavorite.value?.let {
                    favorite -> dataUseCase.insertFavoriteMovie(movie, favorite)
            }
        }

    }


}