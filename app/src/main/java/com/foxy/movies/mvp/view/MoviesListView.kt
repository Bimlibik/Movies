package com.foxy.movies.mvp.view

import com.foxy.movies.data.GenreWrapper
import com.foxy.movies.data.Movie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MoviesListView : MvpView {

    fun onMoviesLoaded(genres: List<GenreWrapper>, movies: List<Movie>)

    fun onMoviesNotAvailable(emptyText: Int)

    fun updateView(genres: List<GenreWrapper>, movies: List<Movie>)

    fun openMovieDetails(id: Int)

    fun showLoading()

}