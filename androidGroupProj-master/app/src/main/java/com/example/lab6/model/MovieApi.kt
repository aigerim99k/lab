package com.example.lab6.model

import com.example.lab6.view.autorization.User
import com.example.lab6.model.json.*
import com.example.lab6.model.json.favorites.FavoriteRequest
import com.example.lab6.model.json.favorites.FavoriteResponse
import com.example.lab6.model.json.movie.Movie
import com.example.lab6.model.json.movie.PopularMovies
import com.example.lab6.model.json.movie.Result
import com.example.lab6.model.json.movie.Session
import com.example.lab6.model.json.movie.Validation
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun getMovieListCoroutine(@Query("api_key") key: String,
                                      @Query("language") lang: String) : Response<PopularMovies>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieByIdCoroutine(@Path("movie_id") movieId: Int,
                              @Query("api_key") key: String,
                              @Query("language") lang: String) : Response<Result>

    @POST("/3/account/{account_id}/favorite")
    suspend fun markFavoriteMovieCoroutine(@Path("account_id") userId: Int,
                          @Query("api_key") key: String,
                          @Query("session_id") sessionId: String,
                          @Body favoriteRequest: FavoriteRequest
    ): Response<FavoriteResponse>

    @GET("/3/account/{account_id}/favorite/movies")
     suspend fun getFavoriteMoviesCoroutine(@Path("account_id") userId: Int,
                          @Query("api_key") key: String,
                          @Query("session_id") sessionId: String,
                          @Query("language") lang: String): Response<PopularMovies>

    //auth
    //new token
    @GET("/3/authentication/token/new")
    fun getNewToken(@Query("api_key") key: String): Call<TokenResponse>
    //validation with token
    @POST("/3/authentication/token/validate_with_login")
    fun validation(
        @Query("api_key") key: String,
        @Body validation: Validation
    ) : Call<TokenResponse>
    //create new session
    @POST("/3/authentication/session/new")
    fun createSession(
        @Query("api_key") key: String,
        @Body  token: TokenResponse) : Call<Session>
    //account
    @GET("account")
    fun getAccount(
        @Query("api_key") key:String,
        @Query("session_id") sessionId: String): Call<User>

}