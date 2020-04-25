 package com.example.lab6.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 //https://api.themoviedb.org/3/movie/popular?api_key=8903dbd0a0cd67d1981d5ee41688dc11 - list of movies
 //https://api.themoviedb.org/3/movie/550?api_key=8903dbd0a0cd67d1981d5ee41688dc11 - single movie
 //https://image.tmdb.org/t/p/w342/4GpwvwDjgwiShr1UBJIn5fk1gwT.jpg - poster path

 object RetrofitService
{
    const val BASE_URL = "https://api.themoviedb.org"

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> getMovieApi(service: Class<T>): T{
        return retrofit.create(service)
    }
}