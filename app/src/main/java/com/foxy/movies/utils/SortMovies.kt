package com.foxy.movies.utils

import com.foxy.movies.data.Movie

enum class SortMovies : Comparator<Movie> {
    BY_NAME {
        override fun compare(movie1: Movie?, movie2: Movie?): Int =
            movie1!!.localizedName.compareTo(movie2!!.localizedName)
    }
}