package com.example.lab6

import com.example.lab6.json.GenreX
import com.example.lab6.json.Movie
import com.example.lab6.json.PopularMovies
import com.example.lab6.json.Result
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("/3/movie/popular")
    fun getMovieList(@Query("api_key") key: String): Call<PopularMovies>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id:Int): Call<Result>

    @GET("/genre/movie/list")
    fun getMovieGenre(@Path("api_key") key: String): Call<GenreX>
}