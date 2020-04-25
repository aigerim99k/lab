package com.example.lab6.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6.BuildConfig
import com.example.lab6.model.MovieApi
import com.example.lab6.model.json.database.MovieDao
import com.example.lab6.model.json.database.MovieDatabase
import com.example.lab6.model.RetrofitService
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class FavoriteListViewModel(private val context: Context) : ViewModel(), CoroutineScope {

    private val job = Job()

    val liveData = MutableLiveData<State>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var movieDao: MovieDao? = null

    init {
        movieDao = MovieDatabase.getDatabase(context = context).movieDao()

    }

    fun getFavorites(){
        launch {
            liveData.value = State.ShowLoading

            val likesOffline = movieDao?.getIdOffline(11)

            for (i in likesOffline!!) {
                val body = JsonObject().apply {
                    addProperty("media_type", "movie")
                    addProperty("media_id", i)
                    addProperty("favorite", true)
                }
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java)
                        .getFavoriteMoviesCoroutine(
                            1,
                            BuildConfig.API_KEY,
                            "1d7900c966a3965dad207c6bd12abf21877b237d",
                            "rus"
                        )
                    if (response.isSuccessful) {
                        val likeMoviesOffline = movieDao?.getMovieOffline(11)
                        for (movie in likeMoviesOffline!!) {
                            movie.liked = 1
                            movieDao?.insert(movie)
                        }
                    }
                } catch (e: Exception) {
                }
            }

            val unLikesOffline = movieDao?.getIdOffline(10)

            for (i in unLikesOffline!!) {
                val body = JsonObject().apply {
                    addProperty("media_type", "movie")
                    addProperty("media_id", i)
                    addProperty("favorite", false)
                }
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java)
                        .markFavoriteMovieCoroutine(
                            1,
                            BuildConfig.API_KEY,
                            "1d7900c966a3965dad207c6bd12abf21877b237d",
                            body
                        )
                    if (response.isSuccessful) {
                        val unlikeMoviesOffline = movieDao?.getMovieOffline(10)
                        for (movie in unlikeMoviesOffline!!) {
                            movie.liked = 0
                            movieDao?.insert(movie)
                        }
                    }
                } catch (e: Exception) {
                }
            }

            val list = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java).getFavoriteMoviesCoroutine(
                        1,
                        BuildConfig.API_KEY,
                        "1d7900c966a3965dad207c6bd12abf21877b237d",
                        "rus"
                    )
                    if (response.isSuccessful) {
                        val result = response.body()!!.results
                        if (result != null) {
                            for (m in result) {
                                m.liked = 1
                            }
                        }
                        if (!result.isNullOrEmpty()) {
                            movieDao?.insertAll(result)
                        }
                        result
                    } else {
                        movieDao?.getAllLiked()
                    }
                } catch (e: Exception) {
                    movieDao?.getAllLiked()
                }
            }
            liveData.value = State.HideLoading
            liveData.value = State.Result(list)
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val list: List<com.example.lab6.model.json.movie.Result>?) : State()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}