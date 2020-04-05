package com.example.lab6.json

import java.io.Serializable

data class PopularMovies(
    val results: List<Result>
)

data class Result(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val runtime: Int,
    val backdrop_path: String,
    val genres: List<Genre>,
    val original_title: String,
    val tagline: String,
    val production_countries: List<ProductionCountryX>
) : Serializable

data class Genre(
    val id: Int,
    val name: String
)
