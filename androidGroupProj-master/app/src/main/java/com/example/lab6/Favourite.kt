package com.example.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide

class Favourite : BaseActivity(1) {

    private val TAG = "FavouriteActivity"

    lateinit var glidePic: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")

        glidePic = findViewById(R.id.glidePic)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg")
            .into(glidePic)

    }
}
