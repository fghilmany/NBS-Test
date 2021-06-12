package com.fghilmany.moviedb.core.domain.repository

import com.fghilmany.moviedb.core.data.Resource
import com.fghilmany.moviedb.core.data.source.local.entity.ComingSoonMovieEntity
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.DetailMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import kotlinx.coroutines.flow.Flow

interface IDataRepository {

    fun getListPopularMovie(): Flow<Resource<List<PopularMovie>>>

    fun getListComingSoonMovie(): Flow<Resource<List<ComingSoonMovie>>>

    fun getMovieCarousel() : Flow<List<PopularMovie>>

    fun getFavoritePopularMovie(): Flow<Resource<List<PopularMovie>>>

    fun getFavoriteComingSoonMovie(): Flow<Resource<List<ComingSoonMovie>>>

    fun getFavoriteMovie(): Flow<List<FavoriteMovie>>

    fun getDetail(idMovie: String): Flow<Resource<DetailMovieResponse>>

    suspend fun insertFavoriteMovie(movie: FavoriteMovie, state: Boolean)

    fun setFavoritePopularMovie(movie: PopularMovie, state: Boolean)

    fun setFavoriteComingSoonMovie(movie: ComingSoonMovie, state: Boolean)

}