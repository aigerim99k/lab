package com.example.lab6.view.favorite

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6.BaseActivity
import com.example.lab6.model.MovieApi
import com.example.lab6.R
import com.example.lab6.model.RetrofitService
import com.example.lab6.model.FavouriteDatabase
import com.example.lab6.model.MovieDao
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class Favourite : BaseActivity(1), CoroutineScope {

    private val TAG = "FavouriteActivity"

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var movieDao: MovieDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        Log.d(TAG, "onCreate")
        setupBottomNavigation()

        movieDao = FavouriteDatabase.getDatabase(context = this@Favourite).movieDao()


        getFavMovieCoroutine()
    }

    fun getFavMovieCoroutine(){
        launch {
            val list = withContext(Dispatchers.IO) {
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java)
                        .getFavoriteMoviesCoroutine(
                            1,
                            getString(R.string.api_key),
                            "1d7900c966a3965dad207c6bd12abf21877b237d",
                            "rus"
                        )
                    if(response.isSuccessful) {
                        val result = response.body()!!.results

                        if(!result.isNullOrEmpty()){
                            movieDao?.insertAll(result)
                        }
                        result
                    }else{
                        movieDao?.getMovies() ?: emptyList()
                    }
                } catch (e: Exception){
                    movieDao?.getMovies() ?: emptyList()
                }
            }
            recyclerViewFav.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@Favourite)
                adapter = FavoriteAdapter(list, this@Favourite)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
