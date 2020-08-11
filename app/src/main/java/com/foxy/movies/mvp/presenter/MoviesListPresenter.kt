package com.foxy.movies.mvp.presenter

import com.foxy.movies.R
import com.foxy.movies.data.ApiClient
import com.foxy.movies.data.Movie
import com.foxy.movies.data.MovieApiInterface
import com.foxy.movies.data.MovieResponse
import com.foxy.movies.mvp.view.MoviesListView
import com.foxy.movies.utils.SortMovies
import moxy.InjectViewState
import moxy.MvpPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class MoviesListPresenter : MvpPresenter<MoviesListView>() {

    private var movies = mutableListOf<Movie>().apply { emptyList<Movie>() }
    private var genres = mutableListOf<String>().apply { emptyList<String>() }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMovies()
    }

    fun showDetails(id: Int) {
        viewState.openMovieDetails(id)
    }

    fun filterByGenre(genre: String) {
        // TODO: show movies by genre
    }

    private fun getGenres(movies: List<Movie>) : List<String>  {
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
                        viewState.onMoviesLoaded(genres, movies)
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
}