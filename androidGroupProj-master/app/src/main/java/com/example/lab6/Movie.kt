package com.example.lab6

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val movieId: Int,
    val movieName: String,
    val genre: String,
    val country: String,
    val title: String,
    val description: String,
    val rating: Double
)