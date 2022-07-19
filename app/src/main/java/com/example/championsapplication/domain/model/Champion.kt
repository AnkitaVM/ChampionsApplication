package com.example.championsapplication.domain.model

import com.google.gson.annotations.SerializedName

data class Champion(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val blurb: String,
    @SerializedName("image")
    val championImage: ChampionImage?
)