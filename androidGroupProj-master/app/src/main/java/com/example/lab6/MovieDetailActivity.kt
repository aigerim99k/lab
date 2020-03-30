package com.example.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.lab6.json.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MovieDetailActivity : AppCompatActivity() {

    lateinit var movieName1: TextView
    lateinit var postImage: ImageView
    lateinit var genre: TextView
    lateinit var rating: TextView
    lateinit var country: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieName1 = findViewById(R.id.movieName1)
        genre = findViewById(R.id.genre)
        rating = findViewById(R.id.rating)
        country= findViewById(R.id.country)
        postImage = findViewById(R.id.postImage)
    }

//    private fun getMovies(id: Int) {
//        RetrofitService.getMovieApi().getMovieById(id).enqueue(object : retrofit2.Callback<Movie> {
//            override fun onFailure(call: Call<Movie>, t: Throwable) {
//                progressBar.visibility = View.GONE
//            }
//
//            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
//                progressBar.visibility = View.GONE
//                val movie = response.body()
//                if (movie != null) {
//                    movieName1.text = movie.title
//                    genre.text = movie.genres.toString()
//                    rating.text = movie.voteAverage.toString()
//                    country.text = movie.productionCountries.toString()
//                }
//            }
//        })
//    }
}
