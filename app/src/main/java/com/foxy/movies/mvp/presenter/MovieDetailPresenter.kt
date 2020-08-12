package com.foxy.movies.mvp.presenter

import com.foxy.movies.data.IMoviesRepository
import com.foxy.movies.data.IMoviesRepository.LoadMovieCallback
import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.view.MovieDetailView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MovieDetailPresenter(private val repository: IMoviesRepository) :
    MvpPresenter<MovieDetailView>() {

    lateinit var movie: Movie

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovie()
    }

    fun goBack() {
        viewState.openMoviesList()
    }

    private fun loadMovie() {
        repository.loadMovie(object : LoadMovieCallback {
            override fun onMovieLoaded(loadedMovie: Movie) {
                movie = loadedMovie
                viewState.onMovieLoaded(movie)
            }

            override fun onMovieNotAvailable() {
                viewState.onMovieNotAvailable()
            }

        })
    }
}