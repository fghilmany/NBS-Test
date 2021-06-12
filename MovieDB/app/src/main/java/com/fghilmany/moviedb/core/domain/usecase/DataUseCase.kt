package com.fghilmany.moviedb.core.domain.usecase

import com.fghilmany.moviedb.core.data.Resource
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import kotlinx.coroutines.flow.Flow

interface DataUseCase {

    fun getListPopularMovie(): Flow<Resource<List<PopularMovie>>>

    fun getListComingSoonMovie() : Flow<Resource<List<ComingSoonMovie>>>

    fun getCarousel(): Flow<List<PopularMovie>>

    fun getFavoritePopularMovie(): Flow<Resource<List<PopularMovie>>>

    fun getFavoriteComingSoonMovie() : Flow<Resource<List<ComingSoonMovie>>>

    fun getFavoriteMovie() : Flow<List<FavoriteMovie>>

    fun getDetail(idMovie: String): Flow<Resource<DetailMovieResponse>>

    fun setPopularFavorite(movie: PopularMovie, state: Boolean)

    fun setComingSoonFavorite(movie: ComingSoonMovie, state: Boolean)

    suspend fun insertFavoriteMovie(movie: FavoriteMovie, state: Boolean)

}