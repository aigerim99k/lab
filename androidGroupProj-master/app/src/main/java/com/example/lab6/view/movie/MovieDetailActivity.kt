package com.example.lab6.view.movie

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lab6.model.MovieApi
import com.example.lab6.R
import com.example.lab6.model.RetrofitService
import com.example.lab6.model.json.favorites.FavoriteRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MovieDetailActivity : AppCompatActivity(), CoroutineScope {

    lateinit var rusTitle: TextView
    lateinit var posterImage: ImageView
    lateinit var titleOriginal: TextView
    lateinit var genres: TextView
    lateinit var release: TextView
    lateinit var tagline: TextView
    lateinit var countries: TextView
    lateinit var runtime: TextView
    lateinit var overview: TextView
    lateinit var rating: TextView
    lateinit var votes: TextView
    lateinit var ratingBar: RatingBar
    lateinit var like: ImageView

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        posterImage = findViewById(R.id.moviePhoto)
        titleOriginal = findViewById(R.id.originalTitle)
        genres = findViewById(R.id.genres)
        tagline = findViewById(R.id.tagline)
        countries = findViewById(R.id.countries)
        runtime = findViewById(R.id.runtime)
        rusTitle = findViewById(R.id.rusTitle)
        overview = findViewById(R.id.overview)
        rating = findViewById(R.id.rating)
        votes = findViewById(R.id.voteCount)
        ratingBar = findViewById(R.id.starRating)
        like = findViewById(R.id.like)

        val movieId = intent.getIntExtra("id", 1)
        val pos = intent.getIntExtra("pos", 1)

        if(flags[pos]){
            like.setImageResource(R.drawable.ic_lliked)
         }else{
            like.setImageResource(R.drawable.ic_like)
         }

        like.setOnClickListener {
            if(flags[pos]){
                like.setImageResource(R.drawable.ic_like)
                flags[pos] = false;
                makeUnFavoriteCoroutine(id=movieId)
            }else{
                like.setImageResource(R.drawable.ic_lliked)
                flags[pos] = true;
                makeFavoriteCoroutine(id=movieId);
            }
        }

        configureBackButton()
        getMovieCoroutine(id=movieId)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getMovieCoroutine(id: Int){
        launch {
            val response = RetrofitService.getMovieApi(
                MovieApi::class.java).getMovieByIdCoroutine(id, getString(R.string.api_key), "ru")
            if(response.isSuccessful){
                val movie = response.body()
                Glide.with(this@MovieDetailActivity)
                    .load("https://image.tmdb.org/t/p/w342${movie?.posterPath}")
                    .into(posterImage)
                var str: String = ""

                for (i in 0..3){
                    str += movie!!.releaseDate[i]
                }

                titleOriginal.text = movie?.originalTitle + "(" + str + ")"
                genres.text = getListOfString(movie?.genres?.map { it.name }.toString().length, movie?.genres?.map { it.name }.toString())
                tagline.text = "«" + movie?.tagline + "»"
                rusTitle.text = movie?.title
                countries.text = getListOfString(movie?.productionCountries?.map { it.iso }.toString().length, movie?.productionCountries?.map { it.iso }.toString())
                runtime.text = movie?.runtime.toString()
                overview.text = movie?.overview
                rating.text = movie?.voteAverage.toString()
                votes.text = movie?.voteCount.toString()
                ratingBar.rating = movie?.voteAverage!!.toFloat()
            }else{
                Toast.makeText(this@MovieDetailActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun getListOfString(len: Int, s: String): String {
        var str: String = ""
        for (i in 1..len-2) {
            str += s[i]
        }
        return str
    }

    private fun configureBackButton(){
        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

    private fun makeFavoriteCoroutine(id: Int){
        launch {
            val response = RetrofitService.getMovieApi(
                MovieApi::class.java).markFavoriteMovieCoroutine(1, getString(R.string.api_key), "1d7900c966a3965dad207c6bd12abf21877b237d",
                FavoriteRequest(
                    "movie",
                    id,
                    true
                )
            )
            if(response.isSuccessful){
                val statMes  = response.body()?.statusMessage.toString()
                Toast.makeText(
                    applicationContext,
                    statMes,
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(this@MovieDetailActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeUnFavoriteCoroutine(id: Int){
        launch {
            val response = RetrofitService.getMovieApi(
                MovieApi::class.java).markFavoriteMovieCoroutine(1, getString(R.string.api_key), "1d7900c966a3965dad207c6bd12abf21877b237d",
                FavoriteRequest(
                    "movie",
                    id,
                    false
                )
            )
            if(response.isSuccessful){
                val statMes  = response.body()?.statusMessage.toString()
                Toast.makeText(
                    applicationContext,
                    statMes,
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(this@MovieDetailActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
