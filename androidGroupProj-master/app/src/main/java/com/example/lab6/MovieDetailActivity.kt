package com.example.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lab6.json.Movie
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        val poster_text: String? = intent.getStringExtra("poster")
        val title_text: String? = intent.getStringExtra("title")
        val overview_text: String? = intent.getStringExtra("overview")
        val release_text: String? = intent.getStringExtra("release")
        val ratingtext: Double? = intent.getDoubleExtra("rating", 0.0)

        val photo: ImageView = findViewById(R.id.moviephoto)
        val title: TextView = findViewById(R.id.movietitle)
        val overview: TextView = findViewById(R.id.movieoverview)
        val rating: TextView = findViewById(R.id.movierating)
        val release: TextView = findViewById(R.id.movierelease)

        Glide.with(this)
            .load(poster_text)
            .into(photo)

        title.text = "Title: $title_text"
        overview.text = "Overview: $overview_text"
        rating.text = "Rating: $ratingtext"
        release.text = "Release date: $release_text"

    }

}
