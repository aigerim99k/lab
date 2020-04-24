package com.example.lab6.view.movie

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lab6.model.MovieApi
import com.example.lab6.R
import com.example.lab6.model.MovieDao
import com.example.lab6.model.MovieDatabase
import com.example.lab6.model.RetrofitService
import com.example.lab6.model.json.favorites.FavoriteRequest
import com.example.lab6.model.json.movie.Result
import com.example.lab6.view_model.MovieDetailViewModel
import com.example.lab6.view_model.ViewModelProviderFactory
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

var isFav = false

class MovieDetailActivity : AppCompatActivity(){

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

    private lateinit var movieDetailsViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        bindViews()

        val movieId = intent.getIntExtra("id", 1)

        configureBackButton()
        getMovieCoroutine(id=movieId)
        setLikes(movieId)
    }

    private fun getMovieCoroutine(id: Int){
        val viewModelProviderFactory = ViewModelProviderFactory(context = this@MovieDetailActivity)
        movieDetailsViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(MovieDetailViewModel::class.java)

        movieDetailsViewModel.getMovie(id)
        movieDetailsViewModel.liveData.observe(this, Observer { result ->
            setData(result)
        })
    }

    private fun makeFavoriteCoroutine(id: Int, favorite: Boolean){
        movieDetailsViewModel.markFavorite(id, favorite)
    }

    private fun setLikes(id: Int) {
        movieDetailsViewModel.setLikes(id)
        setBool(id)
    }

    private fun setData(movie: Result) {
        Glide.with(this@MovieDetailActivity)
            .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
            .into(posterImage)
        var str: String = ""

        for (i in 0..3){
            str += movie.releaseDate[i]
        }

        titleOriginal.text = movie.originalTitle + "(" + str + ")"
        genres.text = getListOfString(movie.genres?.map { it.name }.toString().length, movie.genres?.map { it.name }.toString())
        tagline.text = "«" + movie.tagline + "»"
        rusTitle.text = movie.title
        countries.text = getListOfString(movie.productionCountries?.map { it.iso }.toString().length, movie.productionCountries?.map { it.iso }.toString())
        runtime.text = movie.runtime.toString()
        overview.text = movie.overview
        rating.text = movie.voteAverage.toString()
        votes.text = movie.voteCount.toString()
        ratingBar.rating = movie.voteAverage.toFloat()
    }

    private fun setBool(id: Int){
        if(isFav){
            like.setImageResource(R.drawable.ic_lliked)
        }else{
            like.setImageResource(R.drawable.ic_like)
        }
        like.setOnClickListener {
            if(isFav){
                like.setImageResource(R.drawable.ic_like)
                isFav = false;
                makeFavoriteCoroutine(id = id, favorite = false)
            }else{
                like.setImageResource(R.drawable.ic_lliked)
                isFav = true;
                makeFavoriteCoroutine(id = id, favorite = true);
            }
        }
    }

    fun bindViews(){
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

}
