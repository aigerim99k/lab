package com.example.lab6.json.movie

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Result(
    @PrimaryKey
    @SerializedName("id") val id: Int = 0,
    @SerializedName("poster_path") val poster_path: String = "",
    @SerializedName("release_date") val release_date: String = "",
    @SerializedName("vote_average") val vote_average: Double = 0.0,
    @SerializedName("vote_count") val vote_count: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("original_title") val original_title: String = ""
)