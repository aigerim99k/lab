package com.example.lab6.json.movie

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Result(
    @PrimaryKey
    @SerializedName("id") val id: Int = 0,
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("original_title") val originalTitle: String = ""
)