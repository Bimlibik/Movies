package com.foxy.movies.data

interface IMoviesRepository {

    fun loadMovies(callback: LoadMoviesCallback)

    fun saveMovies(movies: List<Movie>)

    fun loadMovie(callback: LoadMovieCallback)

    fun saveMovie(movie: Movie)

    fun clearCache()


    interface LoadMoviesCallback {
        fun onMoviesLoaded(loadedMovies: List<Movie>)
        fun onMoviesNotAvailable()
    }

    interface LoadMovieCallback {
        fun onMovieLoaded(loadedMovie: Movie)
        fun onMovieNotAvailable()
    }
}