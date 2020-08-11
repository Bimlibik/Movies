package com.foxy.movies.mvp.view

import com.foxy.movies.data.Movie
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MovieDetailView : MvpView {

    fun onMovieLoaded(movie: Movie)

    fun onMovieNotAvailable()

    fun openMoviesList()
}