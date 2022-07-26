package com.example.championsapplication.presentation.uimodels

import com.google.gson.annotations.SerializedName

data class UIChampion(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val blurb: String,
    @SerializedName("image")
    val championImage: UIChampionImage?
)