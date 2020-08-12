package com.foxy.movies.data

import com.foxy.movies.data.IMoviesRepository.LoadMovieCallback
import com.foxy.movies.data.IMoviesRepository.LoadMoviesCallback
import com.foxy.movies.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository : IMoviesRepository {

    override fun loadMovies(callback: LoadMoviesCallback) {
        checkPrefs(callback)
    }

    override fun saveMovies(movies: List<Movie>) {
        saveMoviesToPrefs(movies)
    }

    override fun loadMovie(callback: LoadMovieCallback) {
        val movie = loadMovieFromPrefs()
        if (movie.isEmpty) {
            callback.onMovieNotAvailable()
            return
        }
        callback.onMovieLoaded(movie)
    }

    override fun saveMovie(movie: Movie) {
        saveMovieToPrefs(movie)
    }

    override fun clearCache() {
        clearPrefs()
    }

    private fun loadMoviesFromNetwork(callback: LoadMoviesCallback) {
        val apiClient = ApiClient.client.create(MovieApiInterface::class.java)

        apiClient.getMovies().enqueue(object : Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response != null && response.isSuccessful) {
                    response.body()?.let { callback.onMoviesLoaded(it.movies) }
                } else {
                    callback.onMoviesNotAvailable()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback.onMoviesNotAvailable()
            }
        })
    }

    private fun checkPrefs(callback: LoadMoviesCallback) {
        val movies = loadMoviesFromPrefs()

        if (movies.isEmpty()) {
            loadMoviesFromNetwork(callback)
            return
        }

        callback.onMoviesLoaded(movies)
    }

}