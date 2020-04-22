package com.example.lab6.json.favorites

import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("status_code") val statusCode:Int,
    @SerializedName("status_message") val statusMessage:String
)