package com.fghilmany.moviedb.core.data.source.local.room

import androidx.room.*
import com.fghilmany.moviedb.core.data.source.local.entity.ComingSoonMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.DiscoverMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM discover_movie_entity LIMIT 10")
    fun getDiscoverMovie(): Flow<List<DiscoverMovieEntity>>

    @Query("SELECT * FROM coming_soon_movie_entity LIMIT 10 ")
    fun getComingSoonMovie(): Flow<List<ComingSoonMovieEntity>>

    @Query("SELECT * FROM discover_movie_entity ORDER BY RANDOM() LIMIT 3 ")
    fun getMovieCarousel(): Flow<List<DiscoverMovieEntity>>

    @Query("SELECT * FROM discover_movie_entity WHERE is_favorite = 1 ")
    fun getFavoriteDiscover(): Flow<List<DiscoverMovieEntity>>

    @Query("SELECT * FROM coming_soon_movie_entity WHERE is_favorite = 1 ")
    fun getFavoriteComingSoon(): Flow<List<ComingSoonMovieEntity>>

    @Query("SELECT * FROM favorite_movie_entity WHERE is_favorite = 1 ")
    fun getFavoriteMovie(): Flow<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscoverMovie(movie: List<DiscoverMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComingSoonMovie(movie: List<ComingSoonMovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: FavoriteMovieEntity)

    @Update
    fun updateDiscoverFavorite(movie: DiscoverMovieEntity)

    @Update
    fun updateComingSoonFavorite(movie: ComingSoonMovieEntity)

}