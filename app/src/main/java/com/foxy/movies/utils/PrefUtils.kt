package com.foxy.movies.utils

import androidx.preference.PreferenceManager
import com.foxy.movies.App
import com.foxy.movies.data.Movie
import com.google.gson.Gson

private const val MOVIE = "Movie"
private const val MOVIE_DEFAULT = "Movie not found"


private val prefs by lazy {
    PreferenceManager.getDefaultSharedPreferences(App.get())
}

fun saveMovieToPrefs(movie: Movie) {
    prefs.edit()
        .putString(MOVIE, convertToJson(movie))
        .apply()
}

fun loadMovieFromPrefs(): Movie {
    val json = prefs.getString(MOVIE, MOVIE_DEFAULT)!!
    if (json == MOVIE_DEFAULT) {

    }

    return convertFromJson(json)
}

private fun convertToJson(movie: Movie) = Gson().toJson(movie)

private fun convertFromJson(json: String) = Gson().fromJson(json, Movie::class.java)

