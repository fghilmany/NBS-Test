package com.fghilmany.moviedb.core.domain.model


data class PopularMovie(

    var id: Int = 0,

    val overview: String = "",

    val originalTitle: String = "",

    val title: String = "",

    val posterPath: String = "",

    val backdropPath: String = "",

    var isFavorite: Boolean = false

)