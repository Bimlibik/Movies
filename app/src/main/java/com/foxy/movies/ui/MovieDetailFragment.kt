package com.foxy.movies.ui

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
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
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        onBackPressed()
    }

    override fun onMovieLoaded(movie: Movie) {
        toolbar_title.text = movie.localizedName
        tv_movie_name.text = movie.name
        tv_year.text = resources.getString(R.string.format_movie_year, movie.year)
        tv_rating_value.text = movie.rating
        tv_description.text = movie.description

        Picasso.with(requireContext())
            .load(movie.imgUrl)
            .placeholder(R.drawable.ic_error)
            .resize(150, 150)
            .centerCrop()
            .into(img_movie)

        layout_movie.visibility = View.VISIBLE
        tv_empty_info.visibility = View.GONE
    }

    override fun onMovieNotAvailable() {
        layout_movie.visibility = View.GONE
        tv_empty_info.visibility = View.VISIBLE
    }

    override fun openMoviesList() {
        val action = MoviesListFragmentDirections.actionHome()
        findNavController().navigate(action)
    }

    private fun setupToolbar() {
        val navHost = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHost)
        toolbar.setNavigationOnClickListener { presenter.goBack() }
        toolbar_title.gravity = Gravity.START
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            presenter.goBack()
        }
    }

}