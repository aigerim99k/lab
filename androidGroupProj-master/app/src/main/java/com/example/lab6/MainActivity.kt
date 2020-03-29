package com.example.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import android.R.attr.bottom
import android.content.Intent
import android.util.Log
import androidx.core.view.MenuItemCompat.setIconTintList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.file.Files.size



class MainActivity : BaseActivity(0), MovieAdapter.RecyclerViewItemClick{
    private val TAG = "MainActivity"
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            movieAdapter?.clearAll()
            getMovies()
        }

        movieAdapter = MovieAdapter(itemClickListener = this)
        recyclerView.adapter = movieAdapter

        getMovies()
    }

    override fun itemClick(position: Int, item: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("post_id", item.movieId)
        startActivity(intent)
    }

    private fun getMovies() {
        swipeRefreshLayout.isRefreshing = true
        RetrofitService.getMovieApi().getMovieList().enqueue(object : Callback<List<Movie>> {  ////
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                Log.d("My_post_list", response.body().toString())
                if (response.isSuccessful) {
                    val list = response.body()
                    movieAdapter?.list = list
                    movieAdapter?.notifyDataSetChanged()
                }
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }
}

