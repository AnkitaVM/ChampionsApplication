package com.example.championsapplication.data.model

import com.google.gson.annotations.SerializedName

data class ChampionListResponse(
    @SerializedName("data")
    val championsMap: HashMap<String, Champion>,
) {

}