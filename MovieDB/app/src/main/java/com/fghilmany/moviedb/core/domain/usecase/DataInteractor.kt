package com.fghilmany.moviedb.core.domain.usecase

import com.fghilmany.moviedb.core.data.Resource
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import com.fghilmany.moviedb.core.domain.repository.IDataRepository
import kotlinx.coroutines.flow.Flow

class DataInteractor(private val dataRepository: IDataRepository) :
    DataUseCase {

    override fun getListPopularMovie(): Flow<Resource<List<PopularMovie>>> =
        dataRepository.getListPopularMovie()

    override fun getListComingSoonMovie(): Flow<Resource<List<ComingSoonMovie>>> =
        dataRepository.getListComingSoonMovie()

    override fun getCarousel(): Flow<List<PopularMovie>> =
        dataRepository.getMovieCarousel()

    override fun getFavoritePopularMovie(): Flow<Resource<List<PopularMovie>>> =
        dataRepository.getFavoritePopularMovie()

    override fun getFavoriteComingSoonMovie(): Flow<Resource<List<ComingSoonMovie>>> =
        dataRepository.getFavoriteComingSoonMovie()

    override fun getFavoriteMovie(): Flow<List<FavoriteMovie>> =
        dataRepository.getFavoriteMovie()

    override fun getDetail(idMovie: String): Flow<Resource<DetailMovieResponse>> =
        dataRepository.getDetail(idMovie)

    override fun setPopularFavorite(movie: PopularMovie, state: Boolean) =
        dataRepository.setFavoritePopularMovie(movie, state)

    override fun setComingSoonFavorite(movie: ComingSoonMovie, state: Boolean) =
        dataRepository.setFavoriteComingSoonMovie(movie, state)

    override suspend fun insertFavoriteMovie(movie: FavoriteMovie, state: Boolean) =
        dataRepository.insertFavoriteMovie(movie, state)

}