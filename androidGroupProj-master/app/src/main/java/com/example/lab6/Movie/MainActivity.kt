package com.example.lab6.Movie

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6.*
import com.example.lab6.model.MovieDao
import com.example.lab6.model.MovieDatabase
import kotlinx.android.synthetic.main.activity_main.*
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

        movieDao = MovieDatabase.getDatabase(context = this@MainActivity).movieDao()


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
            progress_bar.visibility = View.GONE
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MoviesAdapter(list, this@MainActivity)
            }
        }
    }
}