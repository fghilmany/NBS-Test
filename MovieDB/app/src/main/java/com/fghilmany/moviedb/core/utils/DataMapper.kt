package com.fghilmany.moviedb.core.utils

import com.fghilmany.moviedb.core.data.source.local.entity.ComingSoonMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.DiscoverMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.FavoriteMovieEntity
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.data.source.remote.response.discover.DiscoverResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.DetailMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie

object DataMapper {

    fun movieMapResponseToEntities(input: DiscoverResponse): List<DiscoverMovieEntity> {
        val movieList = ArrayList<DiscoverMovieEntity>()
        input.results?.map {
            it.apply {
                val movieEntity = DiscoverMovieEntity(
                    id?:0,
                    overview?:"",
                    originalTitle?:"",
                    title?:"",
                    posterPath?:"",
                    backdropPath?:""
                )
                movieList.add(movieEntity)
            }
        }
        return movieList
    }

    fun movieComingSoonMapResponseToEntities(input: DiscoverResponse): List<ComingSoonMovieEntity> {
        val movieList = ArrayList<ComingSoonMovieEntity>()
        input.results?.map {
            it.apply {
                val movieEntity = ComingSoonMovieEntity(
                    id?:0,
                    overview?:"",
                    originalTitle?:"",
                    title?:"",
                    posterPath?:"",
                    backdropPath?:""
                )
                movieList.add(movieEntity)
            }
        }
        return movieList
    }


    fun moviePopularMapEntitiesToDomain(input: List<DiscoverMovieEntity>): List<PopularMovie> =
        input.map {
           with(it) {
                PopularMovie(
                    id, overview, originalTitle, title, posterPath, backdropPath, isFavorite
                )
            }
        }

    fun movieComingSoonMapEntitiesToDomain(input: List<ComingSoonMovieEntity>): List<ComingSoonMovie> =
        input.map {
            with(it) {
                ComingSoonMovie(
                    id, overview, originalTitle, title, posterPath, backdropPath, isFavorite
                )
            }
        }

    fun moviePopularDomainToMapEntities(input: PopularMovie): DiscoverMovieEntity =
        with(input) {
            DiscoverMovieEntity(
                id, overview, originalTitle, title, posterPath, backdropPath, isFavorite
            )
        }

    fun movieComingSoonDomainToMapEntities(input: ComingSoonMovie): ComingSoonMovieEntity =
        with(input) {
            ComingSoonMovieEntity(
                id, overview, originalTitle, title, posterPath, backdropPath, isFavorite
            )
        }

    fun movieMapEntitiesToDomain(input: List<FavoriteMovieEntity>): List<FavoriteMovie> =
        input.map {
            with(it) {
                FavoriteMovie(
                    id, overview, originalTitle, title, posterPath, backdropPath, isFavorite
                )
            }
        }

    fun movieFavoriteDomainToMapEntities(input: FavoriteMovie): FavoriteMovieEntity =
        with(input) {
            FavoriteMovieEntity(
                id, overview, originalTitle, title, posterPath, backdropPath, isFavorite
            )
        }



}