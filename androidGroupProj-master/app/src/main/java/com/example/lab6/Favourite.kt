package com.example.lab6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class Favourite : BaseActivity(1) {

    private val TAG = "FavouriteActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setupBottomNavigation()
        Log.d(TAG, "onCreate")
    }
}
