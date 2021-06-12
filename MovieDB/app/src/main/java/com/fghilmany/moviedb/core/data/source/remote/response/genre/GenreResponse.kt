package com.fghilmany.moviedb.core.data.source.remote.response.genre

import com.google.gson.annotations.SerializedName

data class GenreResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
