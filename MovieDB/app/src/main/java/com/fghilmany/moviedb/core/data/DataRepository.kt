package com.fghilmany.moviedb.core.data

import com.fghilmany.moviedb.core.data.source.local.LocalDataSource
import com.fghilmany.moviedb.core.data.source.remote.RemoteDataSource
import com.fghilmany.moviedb.core.data.source.remote.network.ApiResponse
import com.fghilmany.moviedb.core.data.source.remote.response.detail.DetailMovieResponse
import com.fghilmany.moviedb.core.data.source.remote.response.discover.DiscoverResponse
import com.fghilmany.moviedb.core.domain.model.ComingSoonMovie
import com.fghilmany.moviedb.core.domain.model.FavoriteMovie
import com.fghilmany.moviedb.core.domain.model.PopularMovie
import com.fghilmany.moviedb.core.domain.repository.IDataRepository
import com.fghilmany.moviedb.core.utils.AppExecutors
import com.fghilmany.moviedb.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class DataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IDataRepository{

    override fun getListPopularMovie(): Flow<Resource<List<PopularMovie>>> {
        return object :NetworkBoundResource<List<PopularMovie>, DiscoverResponse>(){
            override fun loadFromDB(): Flow<List<PopularMovie>> {
                return localDataSource.getDiscover().map {
                    DataMapper.moviePopularMapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<PopularMovie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<DiscoverResponse>> =
                remoteDataSource.getPopularMovie()

            override suspend fun saveCallResult(data: DiscoverResponse) {
                val movieList = DataMapper.movieMapResponseToEntities(data)
                localDataSource.insertDiscover(movieList)
            }

        }.asFlow()
    }

    override fun getListComingSoonMovie(): Flow<Resource<List<ComingSoonMovie>>> {
        return object : NetworkBoundResource<List<ComingSoonMovie>, DiscoverResponse>(){
            override fun loadFromDB(): Flow<List<ComingSoonMovie>> =
                localDataSource.getComingSoon().map {
                    DataMapper.movieComingSoonMapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<ComingSoonMovie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<DiscoverResponse>> {
                var year = Calendar.getInstance().get(Calendar.YEAR)
                year += 1
                return remoteDataSource.getComingSoonMovie(year.toString())
            }

            override suspend fun saveCallResult(data: DiscoverResponse) {
                val movieList = DataMapper.movieComingSoonMapResponseToEntities(data)
                localDataSource.insertComingSoon(movieList)
            }

        }.asFlow()
    }

    override fun getMovieCarousel(): Flow<List<PopularMovie>> =
        localDataSource.getMovieCarousel().map {
            DataMapper.moviePopularMapEntitiesToDomain(it)
        }

    override fun getFavoritePopularMovie(): Flow<Resource<List<PopularMovie>>> {
        return object :NetworkBoundResource<List<PopularMovie>, DiscoverResponse>(){
            override fun loadFromDB(): Flow<List<PopularMovie>> {
                return localDataSource.getFavoriteDiscover().map {
                    DataMapper.moviePopularMapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<PopularMovie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<DiscoverResponse>> =
                remoteDataSource.getPopularMovie()

            override suspend fun saveCallResult(data: DiscoverResponse) {
                val movieList = DataMapper.movieMapResponseToEntities(data)
                localDataSource.insertDiscover(movieList)
            }

        }.asFlow()
    }

    override fun getFavoriteComingSoonMovie(): Flow<Resource<List<ComingSoonMovie>>> {
        return object : NetworkBoundResource<List<ComingSoonMovie>, DiscoverResponse>(){
            override fun loadFromDB(): Flow<List<ComingSoonMovie>> =
                localDataSource.getFavoriteComingSoon().map {
                    DataMapper.movieComingSoonMapEntitiesToDomain(it)
                }

            override fun shouldFetch(data: List<ComingSoonMovie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<DiscoverResponse>> {
                var year = Calendar.getInstance().get(Calendar.YEAR)
                year += 1
                return remoteDataSource.getComingSoonMovie(year.toString())
            }

            override suspend fun saveCallResult(data: DiscoverResponse) {
                val movieList = DataMapper.movieComingSoonMapResponseToEntities(data)
                localDataSource.insertComingSoon(movieList)
            }

        }.asFlow()
    }

    override fun getFavoriteMovie(): Flow<List<FavoriteMovie>> =
        localDataSource.getFavoriteMovie().map { DataMapper.movieMapEntitiesToDomain(it) }

    override fun getDetail(idMovie: String): Flow<Resource<DetailMovieResponse>> {
        return object : OnlineResource<DetailMovieResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getDetailMovie(idMovie)
            }
        }.asFlow()
    }

    override suspend fun insertFavoriteMovie(movie: FavoriteMovie, state: Boolean) {
        val favorite = DataMapper.movieFavoriteDomainToMapEntities(movie)
        localDataSource.insertFavorite(favorite, state)
    }

    override fun setFavoritePopularMovie(movie: PopularMovie, state: Boolean) {
        val popular = DataMapper.moviePopularDomainToMapEntities(movie)
        appExecutors.diskIO().execute{ localDataSource.setMovieFavorite(popular, state) }
    }

    override fun setFavoriteComingSoonMovie(movie: ComingSoonMovie, state: Boolean) {
        val comingSoon = DataMapper.movieComingSoonDomainToMapEntities(movie)
        appExecutors.diskIO().execute{ localDataSource.setMovieFavorite(comingSoon, state) }
    }


}