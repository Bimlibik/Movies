package com.foxy.movies.data

data class GenreWrapper(
    val name: String,
    var position: Int,
    var selected: Boolean = false
)