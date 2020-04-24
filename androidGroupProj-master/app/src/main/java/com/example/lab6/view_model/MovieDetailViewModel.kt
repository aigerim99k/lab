package com.example.lab6.view_model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab6.R
import com.example.lab6.model.MovieApi
import com.example.lab6.model.MovieDao
import com.example.lab6.model.MovieDatabase
import com.example.lab6.model.RetrofitService
import com.example.lab6.model.json.favorites.FavoriteRequest
import com.example.lab6.model.json.movie.Result
import com.example.lab6.view.movie.isFav
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

    val liveData = MutableLiveData<Result>()

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
                    ).getMovieByIdCoroutine(id, "8903dbd0a0cd67d1981d5ee41688dc11", "ru")

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
            liveData.value = movieDetail
        }
    }

    fun markFavorite(id: Int, favorite: Boolean) {
        launch {
           try {
                val response = RetrofitService.getMovieApi(
                    MovieApi::class.java
                ).markFavoriteMovieCoroutine(
                    1, "8903dbd0a0cd67d1981d5ee41688dc11", "1d7900c966a3965dad207c6bd12abf21877b237d",
                    FavoriteRequest(
                        "movie",
                        id,
                        favorite
                    )
                )
                if(response.isSuccessful){
                    val statMes = response.body()?.statusMessage.toString()
                    Toast.makeText(
                        context,
                        statMes,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {

            }
        }
    }

    fun setLikes(id: Int){
        launch {
            val response = RetrofitService.getMovieApi(MovieApi::class.java)
                .getFavoriteMoviesCoroutine(
                    1,
                    "8903dbd0a0cd67d1981d5ee41688dc11",
                    "1d7900c966a3965dad207c6bd12abf21877b237d",
                    "rus"
                )

            val movies = response.body()!!.results.map { it.id }
            for(i in 0..movies.size-1) {
                isFav = id == movies[i]
            }
        }
    }

}