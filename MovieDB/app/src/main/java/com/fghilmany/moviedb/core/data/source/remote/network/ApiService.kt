package com.fghilmany.moviedb.core.data.source.remote.network

import com.fghilmany.moviedb.BuildConfig
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.data.source.remote.response.discover.DiscoverResponse
import com.fghilmany.moviedb.core.data.source.remote.response.genre.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getDiscoverMovie(
        @Query("api_key")apiKey: String = BuildConfig.API_KEY,
        @Query("language")language: String = "en-US",
        @Query("sort_by")sortBy: String = "popularity.desc",
        @Query("include_adult")includeAdult: String = "false",
        @Query("include_video")includeVideo: String = "false",
        @Query("page")page: String = "1",
        @Query("year")year: String? = null
    ): DiscoverResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenre(
        @Query("api_key")apiKey: String = BuildConfig.API_KEY,
        @Query("language")language: String = "en-US"
    ): GenreResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")movieId: String,
        @Query("api_key")apiKey: String = BuildConfig.API_KEY,
        @Query("language")language: String = "en-US"
    ): DetailMovieResponse

}