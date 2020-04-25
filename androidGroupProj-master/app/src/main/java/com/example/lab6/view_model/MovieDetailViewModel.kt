package com.example.lab6.view_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6.BuildConfig
import com.example.lab6.model.MovieApi
import com.example.lab6.model.json.database.MovieDao
import com.example.lab6.model.json.database.MovieDatabase
import com.example.lab6.model.RetrofitService
import com.example.lab6.model.json.favorites.FavResponse
import com.example.lab6.model.json.movie.Result
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(
    private val context: Context
) : ViewModel(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val movieDao: MovieDao

    init {
        movieDao = MovieDatabase.getDatabase(context = context).movieDao()
    }

    val liveData = MutableLiveData<State>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getMovie(id: Int) {
        launch {
            val movieDetail = withContext(Dispatchers.IO){
                try {
                    val response = RetrofitService.getMovieApi(
                        MovieApi::class.java
                    ).getMovieByIdCoroutine(id, BuildConfig.API_KEY, "ru")

                    if(response.isSuccessful) {
                        val result = response.body()
                        if(result != null){
                            result.runtime?.let { movieDao.updateMovieRuntime(it, id) }
                            result.tagline?.let { movieDao.updateMovieTagline(it, id) }
                        }
                        result
                    }else{
                        movieDao.getMovieById(id)
                    }
                } catch (e: Exception) {
                    movieDao.getMovieById(id)
                }
            }
            liveData.value = State.Movie(movieDetail)
        }
    }

    fun haslike(movieId: Int?) {
        launch {
            val likeInt = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java)
                        .hasLikeCoroutine(
                            movieId,
                            BuildConfig.API_KEY,
                            "1d7900c966a3965dad207c6bd12abf21877b237d"
                        )
                    Log.d("TAG", response.toString())
                    if (response.isSuccessful) {
                        val gson = Gson()
                        val like = gson.fromJson(
                            response.body(),
                            FavResponse::class.java
                        ).favorite
                        if (like)
                            1
                        else 0
                    } else {
                        movieDao?.getLiked(movieId) ?: 0
                    }
                } catch (e: Exception) {
                    movieDao?.getLiked(movieId) ?: 0
                }
            }
            liveData.value = State.Result(likeInt)
        }
    }

    fun likeMovie(favourite: Boolean, movie: Result?, movieId: Int?) {
        launch {
            val body = JsonObject().apply {
                addProperty("media_type", "movie")
                addProperty("media_id", movieId)
                addProperty("favorite", favourite)
            }
            try {
                RetrofitService.getMovieApi(MovieApi::class.java)
                    .markFavoriteMovieCoroutine(
                        1,
                        BuildConfig.API_KEY, "1d7900c966a3965dad207c6bd12abf21877b237d", body)
            } catch (e: Exception) { }
            if (favourite) {
                movie?.liked = 11
                if (movie != null) {
                    movieDao?.insert(movie)
                }
                Toast.makeText(
                    context,
                    "Movie has been added to favourites",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                movie?.liked = 10
                if (movie != null) {
                    movieDao?.insert(movie)
                }
                Toast.makeText(
                    context,
                    "Movie has been removed from favourites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    sealed class State {
        data class Movie(val movie: com.example.lab6.model.json.movie.Result?) : State()
        data class Result(val likeInt: Int?) : State()
    }
}