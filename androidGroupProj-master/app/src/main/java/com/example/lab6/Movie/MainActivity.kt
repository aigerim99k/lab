package com.example.lab6.Movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab6.*
import com.example.lab6.json.Genre
import com.example.lab6.json.PopularMovies
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

val flags = arrayOf(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false)

class MainActivity : BaseActivity(0), CoroutineScope {
    private val TAG = "MainActivity"

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate")

        setupBottomNavigation()

        getMoviesCoroutine()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getMoviesCoroutine(){
        launch {
            val response = RetrofitService.getMovieApi(MovieApi::class.java).getMovieListCoroutine(getString(R.string.api_key), "ru")
            if(response.isSuccessful){
                progress_bar.visibility = View.GONE
                recyclerView.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = MoviesAdapter(response.body()!!.results, this@MainActivity)
                }
            }else{
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

