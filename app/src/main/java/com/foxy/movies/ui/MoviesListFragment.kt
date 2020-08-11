package com.foxy.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.foxy.movies.R
import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.presenter.MoviesListPresenter
import com.foxy.movies.mvp.view.MoviesListView
import com.foxy.movies.ui.adapters.MoviesAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter


class MoviesListFragment : MvpAppCompatFragment(), MoviesListView {

    @InjectPresenter
    lateinit var presenter: MoviesListPresenter

    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        moviesAdapter = MoviesAdapter(ArrayList(0), ArrayList(0), presenter)

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (moviesAdapter.getItemViewType(position)) {
                    MoviesAdapter.ITEM_MOVIE_VIEW -> 1
                    else -> 2
                }
            }
        }

        recycler_movies.apply {
            adapter = moviesAdapter
            layoutManager = gridLayoutManager
        }
    }

    override fun onMoviesLoaded(genres: List<String>, movies: List<Movie>) {
        moviesAdapter.genres = genres
        moviesAdapter.movies = movies
        tv_empty_info.visibility = View.GONE
        recycler_movies.visibility = View.VISIBLE
    }

    override fun onMoviesNotAvailable(emptyText: Int) {
        tv_empty_info.visibility = View.VISIBLE
        recycler_movies.visibility = View.GONE
    }

    override fun updateView(genres: List<String>, movies: List<Movie>) {
        moviesAdapter.genres = genres
        moviesAdapter.movies = movies
    }

    override fun openMovieDetails(id: Int) {
        val action = MoviesListFragmentDirections.actionMoviesListToMovieDetail(id)
        findNavController().navigate(action)
    }

    private fun setupToolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        toolbar.title = getString(R.string.app_name)
    }
}