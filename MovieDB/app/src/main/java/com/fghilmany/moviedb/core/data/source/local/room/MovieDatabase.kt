package com.fghilmany.moviedb.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fghilmany.moviedb.core.data.source.local.entity.ComingSoonMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.DiscoverMovieEntity
import com.fghilmany.moviedb.core.data.source.local.entity.FavoriteMovieEntity

@Database(
    entities = [DiscoverMovieEntity::class, ComingSoonMovieEntity::class, FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}