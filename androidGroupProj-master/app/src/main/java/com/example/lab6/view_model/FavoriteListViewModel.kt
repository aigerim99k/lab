package com.example.lab6.view_model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6.R
import com.example.lab6.model.FavouriteDatabase
import com.example.lab6.model.MovieApi
import com.example.lab6.model.MovieDao
import com.example.lab6.model.RetrofitService
import com.example.lab6.model.json.movie.Result
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
        movieDao = FavouriteDatabase.getDatabase(context = context).movieDao()

    }

    fun getFavorites(){
        launch {
            liveData.value = State.ShowLoading
            val list = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java)
                        .getFavoriteMoviesCoroutine(
                            1,
                            "8903dbd0a0cd67d1981d5ee41688dc11",
                            "1d7900c966a3965dad207c6bd12abf21877b237d",
                            "rus"
                        )
                    if (response.isSuccessful) {
                        val result = response.body()!!.results

                        if (!result.isNullOrEmpty()) {
                            movieDao?.insertAll(result)
                        }
                        result
                    } else {
                        movieDao?.getMovies() ?: emptyList()
                    }
                } catch (e: Exception) {
                    movieDao?.getMovies() ?: emptyList()
                }
            }
            liveData.value = State.HideLoading
            liveData.value = State.Result(list)
        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val list: List<com.example.lab6.model.json.movie.Result>) : State()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}