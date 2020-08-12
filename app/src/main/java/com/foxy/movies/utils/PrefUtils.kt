package com.foxy.movies.utils

import androidx.preference.PreferenceManager
import com.foxy.movies.App
import com.foxy.movies.data.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val MOVIES = "Movies"
private const val MOVIE = "Movie"
private const val MOVIE_DEFAULT = "Movie not found"


private val prefs by lazy {
    PreferenceManager.getDefaultSharedPreferences(App.get())
}

fun clearPrefs() {
    prefs.edit()
        .remove(MOVIES)
        .remove(MOVIE)
        .apply()
}

fun saveMoviesToPrefs(movies: List<Movie>) {
    prefs.edit()
        .putString(MOVIES, convertMoviesToJson(movies))
        .apply()
}

fun loadMoviesFromPrefs() : List<Movie> {
    val json = prefs.getString(MOVIES, MOVIE_DEFAULT)!!
    return convertMoviesFromJson(json)
}

fun saveMovieToPrefs(movie: Movie) {
    prefs.edit()
        .putString(MOVIE, convertMovieToJson(movie))
        .apply()
}

fun loadMovieFromPrefs(): Movie {
    val json = prefs.getString(MOVIE, MOVIE_DEFAULT)!!
    if (json == MOVIE_DEFAULT) {
        return Movie()
    }

    return convertMovieFromJson(json)
}

private fun convertMoviesToJson(movies: List<Movie>) = Gson().toJson(movies)

private fun convertMoviesFromJson(json: String): List<Movie> {
    if (json.isEmpty() || json == MOVIE_DEFAULT) return emptyList()

    val type = object : TypeToken<List<Movie>>() {}.type
    return Gson().fromJson(json, type)
}

private fun convertMovieToJson(movie: Movie) = Gson().toJson(movie)

private fun convertMovieFromJson(json: String) = Gson().fromJson(json, Movie::class.java)

