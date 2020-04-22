package com.example.lab6.json.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Result(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val runtime: Int,
    val backdrop_path: String,
//    val genres: List<Genre>,
//    val genre_ids: List<Int>,
    val original_title: String,
    val tagline: String
//    val production_countries: List<ProductionCountryX>
)