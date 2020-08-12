package com.foxy.movies.mvp.presenter

import com.foxy.movies.R
import com.foxy.movies.data.*
import com.foxy.movies.mvp.view.MoviesListView
import com.foxy.movies.utils.SortMovies
import com.foxy.movies.utils.saveMovieToPrefs
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class MoviesListPresenter : MvpPresenter<MoviesListView>() {

    private var movies = mutableListOf<Movie>().apply { emptyList<Movie>() }
    private var genres = mutableListOf<String>().apply { emptyList<String>() }
    private var genresWrapper = mutableListOf<GenreWrapper>().apply { emptyList<String>() }
    private var selectedPosition = NOT_SELECTED

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovies()
    }

    fun showDetails(movie: Movie) {
        saveMovieToPrefs(movie)
        viewState.openMovieDetails(movie.id)
    }

    fun filterByGenre(genreFilter: GenreWrapper) {
        if (genreFilter.position == selectedPosition) {
            genreFilter.selected = false
            selectedPosition = NOT_SELECTED
            viewState.updateView(genresWrapper, movies)
            return
        }

        checkSelected()
        genreFilter.selected = true
        selectedPosition = genreFilter.position
        viewState.updateView(genresWrapper, getFilteredMovies(genreFilter.name))
    }

    private fun getFilteredMovies(filter: String): List<Movie> {
        val filteredMovies = mutableListOf<Movie>()
        for (movie in movies) {
            for (genre in movie.genres) {
                if (genre == filter) filteredMovies.add(movie)
            }
        }
        return filteredMovies
    }

    private fun checkSelected() {
        if (selectedPosition == NOT_SELECTED) return
        genresWrapper[selectedPosition].selected = false
    }

    private fun getGenresWrapper(genres: List<String>): List<GenreWrapper> {
        val newGenresWrapper = mutableListOf<GenreWrapper>()
        for ((i, genre) in genres.withIndex()) {
            newGenresWrapper.add(GenreWrapper(genre, i))
        }
        genresWrapper.clear()
        genresWrapper.addAll(newGenresWrapper)
        return newGenresWrapper
    }

    private fun getGenres(movies: List<Movie>): List<String> {
        val genres = mutableListOf<String>()
        for (movie in movies) {
            for (genre in movie.genres) {
                if (genres.contains(genre)) continue
                genres.add(genre)
            }
        }
        return genres
    }

    private fun loadMovies() {
        viewState.showLoading()

        val apiClient = ApiClient.client.create(MovieApiInterface::class.java)

        apiClient.getMovies().enqueue(object : Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response != null && response.isSuccessful) {
                    response.body()?.let {
                        movies.clear()
                        movies.addAll(it.movies)
                        movies.sortWith(SortMovies.BY_NAME)
                        genres.clear()
                        genres.addAll(getGenres(movies))
                        viewState.onMoviesLoaded(getGenresWrapper(genres), movies)
                    }
                } else {
                    viewState.onMoviesNotAvailable(R.string.empty_text)
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                viewState.onMoviesNotAvailable(R.string.empty_text)
            }

        })
    }

    companion object {
        const val NOT_SELECTED = -1
    }
}