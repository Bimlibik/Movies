package com.foxy.movies.mvp.presenter

import com.foxy.movies.R
import com.foxy.movies.data.GenreWrapper
import com.foxy.movies.data.IMoviesRepository
import com.foxy.movies.data.IMoviesRepository.LoadMoviesCallback
import com.foxy.movies.data.Movie
import com.foxy.movies.mvp.view.MoviesListView
import com.foxy.movies.utils.SortMovies
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class MoviesListPresenter(private val repository: IMoviesRepository) :
    MvpPresenter<MoviesListView>() {

    private var movies = mutableListOf<Movie>().apply { emptyList<Movie>() }
    private var genres = mutableListOf<String>().apply { emptyList<String>() }
    private var genresWrapper = mutableListOf<GenreWrapper>().apply { emptyList<String>() }
    private var selectedPosition = NOT_SELECTED

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovies()
    }

    fun clearCache() {
        repository.clearCache()
    }

    fun showDetails(movie: Movie) {
        repository.saveMovie(movie)
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

    fun reloadMovies() {
        repository.clearCache()
        loadMovies(true)
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

    private fun loadMovies(forceUpdate: Boolean = false) {
        if (!forceUpdate) viewState.showLoading()

        repository.loadMovies(forceUpdate, object : LoadMoviesCallback {
            override fun onMoviesLoaded(loadedMovies: List<Movie>) {
                movies.clear()
                movies.addAll(loadedMovies)
                movies.sortWith(SortMovies.BY_NAME)
                genres.clear()
                genres.addAll(getGenres(movies))
                viewState.onMoviesLoaded(getGenresWrapper(genres), movies)
                repository.saveMovies(movies)
            }

            override fun onMoviesNotAvailable() {
                viewState.onMoviesNotAvailable(R.string.empty_text)
            }
        })
    }

    companion object {
        const val NOT_SELECTED = -1
    }
}