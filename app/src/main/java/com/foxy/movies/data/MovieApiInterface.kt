package com.foxy.movies.data

import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {
    @GET("films.json")
    fun getMovies(): Call<MovieResponse>
}