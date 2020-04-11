package com.example.lab6.favorite

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Favourite : BaseActivity(1) {

    private val TAG = "FavouriteActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        getFavMovie()
    }

    fun getFavMovie(){
        RetrofitService.getMovieApi(MovieApi::class.java).getFavoriteMovies(1,getString(R.string.api_key),"1d7900c966a3965dad207c6bd12abf21877b237d", "rus")
            .enqueue(object : Callback<PopularMovies>{
                override fun onFailure(call: Call<PopularMovies>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<PopularMovies>,
                    response: Response<PopularMovies>
                ) {
                    if (response.isSuccessful){
                        recyclerViewFav.apply {
                            setHasFixedSize(true)
                            layoutManager = LinearLayoutManager(this@Favourite)
                            adapter = FavoriteAdapter(response.body()!!.results, this@Favourite)
                        }
                    }
                }

            })
    }
}
