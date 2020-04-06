package com.example.lab6.Movie

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lab6.MovieApi
import com.example.lab6.R
import com.example.lab6.RetrofitService
import com.example.lab6.json.Result
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailActivity : AppCompatActivity() {

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        posterImage = findViewById(R.id.moviephoto)
        titleOriginal = findViewById(R.id.originalTitle)
        release = findViewById(R.id.movieRelease)
        genres = findViewById(R.id.genres)
        tagline = findViewById(R.id.tagline)
        countries = findViewById(R.id.countries)
        runtime = findViewById(R.id.runtime)
        rusTitle = findViewById(R.id.RusTitle)
        overview = findViewById(R.id.overview)
        rating = findViewById(R.id.rating)
        votes = findViewById(R.id.voteCount)
        ratingBar = findViewById(R.id.starRating)
        val movieId = intent.getIntExtra("id", 1)

        configureBackButton()
        getMovie(id=movieId)
    }

    fun getMovie(id: Int){
        RetrofitService.getMovieApi(MovieApi::class.java)
            .getMovieById(id, getString(R.string.api_key), "rus").enqueue(
            object : Callback<Result> {

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val movie = response.body()
                Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/w342${movie?.poster_path}")
                        .into(posterImage)

                titleOriginal.text = movie?.original_title
                release.text = "(" + movie?.release_date + ")"
                genres.text = movie?.genres?.map { it.name }.toString()
                tagline.text = movie?.tagline
                rusTitle.text = movie?.title
                countries.text = movie?.production_countries?.map { it.iso_3166_1 }.toString()
                runtime.text = movie?.runtime.toString()
                overview.text = movie?.overview
                rating.text = movie?.vote_average.toString()
                votes.text = movie?.vote_count.toString()
                ratingBar.rating = movie?.vote_average!!.toFloat()
            }

            override fun onFailure(call: Call<Result>, t: Throwable){
                Toast.makeText(this@MovieDetailActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun configureBackButton(){
        val back: ImageView
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

}
