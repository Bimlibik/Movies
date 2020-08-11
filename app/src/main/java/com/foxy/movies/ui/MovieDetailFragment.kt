package com.foxy.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foxy.movies.R
import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.presenter.MovieDetailPresenter
import com.foxy.movies.mvp.view.MovieDetailView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter

class MovieDetailFragment : MvpAppCompatFragment(), MovieDetailView {

    @InjectPresenter
    lateinit var presenter: MovieDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMovieLoaded(movie: Movie) {
        TODO("TODO")
    }

    override fun onMovieNotAvailable() {
        TODO("TODO")
    }

    override fun openMoviesList() {
        TODO("TODO")
    }

}