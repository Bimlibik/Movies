package com.foxy.movies.data

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("films")
    val movies: List<Movie>

)