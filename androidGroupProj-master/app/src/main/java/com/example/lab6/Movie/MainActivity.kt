package com.example.lab6.Movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab6.*
import com.example.lab6.json.Genre
import com.example.lab6.json.MovieDao
import com.example.lab6.json.MovieDatabase
import com.example.lab6.json.PopularMovies
import com.example.lab6.json.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        movieDao = MovieDatabase.getDatabase(context = this).movieDao()

        Log.d(TAG, "onCreate")

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
                        val result = response.body()

                        if (!result.isNullOrEmpty()) {
                            movieDao?.insertAll(result)
                        }
                        result
                    }else{
                        movieDao?.getAll() ?: emptyList()
                    }
                } catch (e: Exception){
                    movieDao?.getAll() ?: emptyList<Result>()
                }
            }
            progress_bar.visibility = View.GONE
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = list?.let { MoviesAdapter(it, this@MainActivity) }
            }
        }

    }
}