package com.example.championsapplication.domain.repository

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result

interface ChampionsRepository {
    suspend fun getChampions(): Result<List<Champion>>
    suspend fun getChampionDetails(chId: String): Result<Champion>
}