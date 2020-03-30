package com.example.lab6

import com.example.lab6.json.PopularMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    fun getMovieList(@Query("api_key") key: String): Call<PopularMovies>
}