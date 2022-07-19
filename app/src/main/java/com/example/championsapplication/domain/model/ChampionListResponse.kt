package com.example.championsapplication.domain.model

import com.google.gson.annotations.SerializedName

data class ChampionListResponse(
    @SerializedName("data")
    val championsMap: HashMap<String, Champion>,
)