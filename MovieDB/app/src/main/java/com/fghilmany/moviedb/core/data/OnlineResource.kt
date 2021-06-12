package com.fghilmany.moviedb.core.data

import android.util.Log
import com.fghilmany.moviedb.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class OnlineResource<ResultType> {
    private var result: Flow<Resource<ResultType>> = flow {

        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResponse.data))
            }
            is ApiResponse.Empty -> {}
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
                Log.e("OnlineResource", apiResponse.errorMessage)
            }
        }

    }

    protected abstract suspend fun createCall(): Flow<ApiResponse<ResultType>>

    fun asFlow(): Flow<Resource<ResultType>> = result
}