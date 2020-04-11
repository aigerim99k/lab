package com.example.lab6

import com.example.lab6.json.*
import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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

    @POST("/3/account/{account_id}/favorite")
    fun markFavoriteMovie(@Path("account_id") userId: Int,
                          @Query("api_key") key: String,
                          @Query("session_id") session_id: String,
                          @Body favoriteRequest: FavoriteRequest): Call<FavoriteResponse>

    @GET("/3/account/{account_id}/favorite/movies")
    fun getFavoriteMovies(@Path("account_id") userId: Int,
                          @Query("api_key") key: String,
                          @Query("session_id") session_id: String,
                          @Query("language") lang: String): Call<PopularMovies>
}