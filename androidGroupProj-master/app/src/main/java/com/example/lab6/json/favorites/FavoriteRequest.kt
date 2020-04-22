package com.example.lab6.json.favorites

import com.google.gson.annotations.SerializedName


data class FavoriteRequest (
    @SerializedName("media_type") val media_type: String,
    @SerializedName("media_id") val media_id: Int,
    @SerializedName("favorite") val favorite: Boolean
)
