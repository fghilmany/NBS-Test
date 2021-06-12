package com.fghilmany.moviedb.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_entity")
data class FavoriteMovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "original_title")
    var originalTitle: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "poster_path")
    var posterPath: String = "",

    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String = "",

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false

)