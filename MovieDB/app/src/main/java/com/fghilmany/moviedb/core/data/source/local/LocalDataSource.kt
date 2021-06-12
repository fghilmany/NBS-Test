package com.fghilmany.moviedb.core.data.source.local

import com.fghilmany.moviedb.core.data.source.local.entity.ComingSoonMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.DiscoverMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.FavoriteMovieEntity
import com.fghilmany.moviedb.core.data.source.local.room.MovieDao

class LocalDataSource(private val movieDao: MovieDao) {

    fun getDiscover() = movieDao.getDiscoverMovie()

    fun getComingSoon() = movieDao.getComingSoonMovie()

    fun getMovieCarousel() = movieDao.getMovieCarousel()

    fun getFavoriteDiscover() = movieDao.getFavoriteDiscover()

    fun getFavoriteComingSoon() = movieDao.getComingSoonMovie()

    fun getFavoriteMovie() = movieDao.getFavoriteMovie()

    suspend fun insertDiscover(movie: List<DiscoverMovieEntity>) =
        movieDao.insertDiscoverMovie(movie)

    suspend fun insertComingSoon(movie: List<ComingSoonMovieEntity>) =
        movieDao.insertComingSoonMovie(movie)

    suspend fun insertFavorite(movie: FavoriteMovieEntity, state: Boolean) {
        movie.isFavorite = state
        movieDao.insertFavoriteMovie(movie)
    }

    fun setMovieFavorite(movie: DiscoverMovieEntity, newState: Boolean){
        movie.isFavorite = newState
        movieDao.updateDiscoverFavorite(movie)
    }

    fun setMovieFavorite(comingSoon: ComingSoonMovieEntity, newState: Boolean){
        comingSoon.isFavorite = newState
        movieDao.updateComingSoonFavorite(comingSoon)
    }

}