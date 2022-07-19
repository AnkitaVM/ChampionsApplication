package com.example.championsapplication.data.api

import com.example.championsapplication.domain.model.ChampionListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ChampionsApi {

    @GET(CHAMPIONS_LIST)
    suspend fun getChampionsList(): Response<ChampionListResponse>
}