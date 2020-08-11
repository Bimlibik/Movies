package com.foxy.movies.mvp.presenter

import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.view.MovieDetailView
import com.foxy.movies.utils.loadMovieFromPrefs
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MovieDetailPresenter : MvpPresenter<MovieDetailView>() {

    lateinit var movie: Movie

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovie()
    }

    private fun loadMovie() {
        movie = loadMovieFromPrefs()
        viewState.onMovieLoaded(movie)
    }
}