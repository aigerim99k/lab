package com.example.lab6.Movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.*
import com.example.lab6.model.MovieDao
import com.example.lab6.model.MovieDatabase
import com.example.lab6.json.movie.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

val flags = arrayOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)

class MainActivity : BaseActivity(0), CoroutineScope{
    private val TAG = "MainActivity"
    private val job = Job()

    private var movieDao: MovieDao? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        movieDao = MovieDatabase.getDatabase(context = this).movieDao()


        setupBottomNavigation()
        getMoviesCoroutine()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getMoviesCoroutine() {
        launch {
            val list = withContext(Dispatchers.IO){
                try {
                    val response = RetrofitService.getMovieApi(MovieApi::class.java).getMovieListCoroutine(getString(R.string.api_key), "ru")
                    if(response.isSuccessful) {
                        val result = response.body()!!.results

                        if(result.isNullOrEmpty()){
                            movieDao?.insertAll(result)
                        }
                        result
                    }else{
                        movieDao?.getAll() ?: emptyList()
                    }
                } catch (e: Exception){
                    movieDao?.getAll() ?: emptyList()
                }
            }
            progress_bar.visibility = View.GONE
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MoviesAdapter(list, this@MainActivity)
            }
        }

    }

}