package com.example.lab6.favorite

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.lab6.BaseActivity
import com.example.lab6.Movie.MoviesAdapter
import com.example.lab6.MovieApi
import com.example.lab6.R
import com.example.lab6.RetrofitService
import com.example.lab6.json.PopularMovies
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class Favourite : BaseActivity(1), CoroutineScope {

    private val TAG = "FavouriteActivity"

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        getFavMovieCoroutine()
    }

    fun getFavMovieCoroutine(){
        launch {
            val response = RetrofitService.getMovieApi(MovieApi::class.java).getFavoriteMoviesCoroutine(1,getString(R.string.api_key),"1d7900c966a3965dad207c6bd12abf21877b237d", "rus")
            if(response.isSuccessful){
                recyclerViewFav.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@Favourite)
                    adapter = FavoriteAdapter(response.body()!!.results, this@Favourite)
                }
            }else{
                Toast.makeText(this@Favourite, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
