package com.fghilmany.moviedb.core.data.source.remote

import com.fghilmany.moviedb.core.data.source.remote.network.ApiResponse
import com.fghilmany.moviedb.core.data.source.remote.network.ApiService
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.data.source.remote.response.discover.DiscoverResponse
import com.fghilmany.moviedb.core.data.source.remote.response.genre.GenreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(private val apiService: ApiService?) {

    suspend fun getPopularMovie(): Flow<ApiResponse<DiscoverResponse>> {
        return flow {
            try {
                val response = apiService?.getDiscoverMovie()
                if (response?.results != null){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag("getPopularMovie").e( e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getComingSoonMovie(year: String): Flow<ApiResponse<DiscoverResponse>> {
        return flow {
            try {
                val response = apiService?.getDiscoverMovie(year = year)
                if (response?.results != null){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag("getComingSoonMovie").e( e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailMovie(id: String): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService?.getMovieDetail(movieId = id)
                if (response != null){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag("getDetailMovie").e( e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieGenre(): Flow<ApiResponse<GenreResponse>> {
        return flow {
            try {
                val response = apiService?.getMovieGenre()
                if (response?.genres != null){
                    emit(ApiResponse.Success(response))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag("getMovieGnre").e( e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}