package com.foxy.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foxy.movies.R
import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.presenter.MovieDetailPresenter
import com.foxy.movies.mvp.view.MovieDetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.toolbar.*
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
        tv_movie_name.text = movie.name
        tv_year.text = resources.getString(R.string.format_movie_year, movie.year)
        tv_rating_value.text = movie.rating
        tv_description.text = movie.description

        Picasso.with(requireContext())
            .load(movie.imgUrl)
            .placeholder(R.drawable.ic_error)
            .into(img_movie)

        layout_movie.visibility = View.VISIBLE
        tv_empty_info.visibility = View.GONE
    }

    override fun onMovieNotAvailable() {
        layout_movie.visibility = View.GONE
        tv_empty_info.visibility = View.VISIBLE
    }

    override fun openMoviesList() {
        TODO("TODO")
    }

}