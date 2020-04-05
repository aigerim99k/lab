package com.example.lab6

import com.example.lab6.json.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    fun getMovieList(@Query("api_key") key: String,
                     @Query("language") lang: String): Call<PopularMovies>

    @GET("/3/movie/{movie_id}")
    fun getMovieById(@Path("movie_id") movieId: Int,
                     @Query("api_key") key: String,
                     @Query("language") lang: String): Call<Result>

    @GET("/3/genre/movie/list")
    fun getMovieGenre(@Path("api_key") key: String): Call<Genre>
}