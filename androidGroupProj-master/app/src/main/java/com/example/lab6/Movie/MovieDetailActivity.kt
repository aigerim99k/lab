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
import com.example.lab6.json.FavoriteRequest
import com.example.lab6.json.FavoriteResponse
import com.example.lab6.json.Result
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
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
    lateinit var like: ImageView
    var isFavorit: Boolean = false

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
        like = findViewById(R.id.like)

        val movieId = intent.getIntExtra("id", 1)
        val pos = intent.getIntExtra("pos", 1)

        if(flags[pos] == true){
            like.setImageResource(R.drawable.ic_lliked)
         }else{
            like.setImageResource(R.drawable.ic_like)
         }

        like.setOnClickListener {
            if(flags[pos] == true){
                like.setImageResource(R.drawable.ic_like)
                flags[pos] = false;
                makeUnFavorite(id=movieId)
            }else{
                like.setImageResource(R.drawable.ic_lliked)
                flags[pos] = true;
                makeFavorite(id=movieId);
            }
        }

        configureBackButton()
        getMovie(id=movieId)
    }

    fun getMovie(id: Int){
        RetrofitService.getMovieApi(MovieApi::class.java)
            .getMovieById(id, getString(R.string.api_key), "ru").enqueue(
            object : Callback<Result> {

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                val movie = response.body()
                Glide.with(this@MovieDetailActivity)
                        .load("https://image.tmdb.org/t/p/w342${movie?.poster_path}")
                        .into(posterImage)

                var str: String = ""

                for (i in 0..3){
                    str += movie!!.release_date[i]
                }

                titleOriginal.text = movie?.original_title
                release.text = "(" + str + ")"
                genres.text = getListOfString(movie?.genres?.map { it.name }.toString().length, movie?.genres?.map { it.name }.toString())
                tagline.text = "«" + movie?.tagline + "»"
                rusTitle.text = movie?.title
                countries.text = getListOfString(movie?.production_countries?.map { it.iso_3166_1 }.toString().length, movie?.production_countries?.map { it.iso_3166_1 }.toString())
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

    fun getListOfString(len: Int, s: String): String {
        var str: String = ""
        for (i in 1..len-2) {
            str += s[i]
        }
        return str
    }

    fun configureBackButton(){
        val back: ImageView
        back = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }

    fun makeFavorite(id: Int){
        RetrofitService.getMovieApi(MovieApi::class.java).markFavoriteMovie(1, getString(R.string.api_key), "1d7900c966a3965dad207c6bd12abf21877b237d",
        FavoriteRequest("movie", id, true)).enqueue(
            object : Callback<FavoriteResponse>{
                override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<FavoriteResponse>,
                    response: Response<FavoriteResponse>
                ) {
                    val statMes  = response.body()?.status_message.toString()
                    Toast.makeText(
                        applicationContext,
                        statMes,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun makeUnFavorite(id: Int){
        RetrofitService.getMovieApi(MovieApi::class.java).markFavoriteMovie(1, getString(R.string.api_key), "1d7900c966a3965dad207c6bd12abf21877b237d",
            FavoriteRequest("movie", id, false)).enqueue(
            object : Callback<FavoriteResponse>{
                override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<FavoriteResponse>,
                    response: Response<FavoriteResponse>
                ) {
                    val statMes  = response.body()?.status_message.toString()
                    Toast.makeText(
                        applicationContext,
                        statMes,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
