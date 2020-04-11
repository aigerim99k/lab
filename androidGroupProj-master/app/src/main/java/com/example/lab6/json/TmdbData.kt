package com.example.lab6.json

import com.google.gson.annotations.SerializedName
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
    val genre_ids: List<Int>,
    val original_title: String,
    val tagline: String,
    val production_countries: List<ProductionCountryX>
) : Serializable

data class Genre(
    val id: Int,
    val name: String
)

data class FavoriteResponse(
    val status_code:Int,
    val status_message:String
)

data class FavoriteRequest (
    @SerializedName("media_type") val media_type: String,
    @SerializedName("media_id") val media_id: Int,
    @SerializedName("favorite") val favorite: Boolean
)
