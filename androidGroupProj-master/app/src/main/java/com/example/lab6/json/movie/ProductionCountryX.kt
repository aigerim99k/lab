package com.example.lab6.json.movie


import com.google.gson.annotations.SerializedName

data class ProductionCountryX(
    @SerializedName("iso_3166_1") val iso: String,
    @SerializedName("name") val name: String
)