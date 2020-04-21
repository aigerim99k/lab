package com.example.lab6

import com.example.lab6.autorization.User
import com.example.lab6.json.*
import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MovieApi {
    @GET("/3/movie/popular")
    suspend fun getMovieListCoroutine(@Query("api_key") key: String,
                                      @Query("language") lang: String) : Response<List<Result>>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieByIdCoroutine(@Path("movie_id") movieId: Int,
                              @Query("api_key") key: String,
                              @Query("language") lang: String) : Response<Movie>

    @POST("/3/account/{account_id}/favorite")
    suspend fun markFavoriteMovieCoroutine(@Path("account_id") userId: Int,
                          @Query("api_key") key: String,
                          @Query("session_id") session_id: String,
                          @Body favoriteRequest: FavoriteRequest): Response<FavoriteResponse>

    @GET("/3/account/{account_id}/favorite/movies")
     suspend fun getFavoriteMoviesCoroutine(@Path("account_id") userId: Int,
                          @Query("api_key") key: String,
                          @Query("session_id") session_id: String,
                          @Query("language") lang: String): Response<PopularMovies>

    //auth
    //new token
    @GET("/3/authentication/token/new")
    fun getNewToken(@Query("api_key") key: String): Call<TokenResponse>
    //validation with token
    @POST("/3/authentication/token/validate_with_login")
    fun validation(
        @Query("api_key") key: String,
        @Body validation: Validation) : Call<TokenResponse>
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