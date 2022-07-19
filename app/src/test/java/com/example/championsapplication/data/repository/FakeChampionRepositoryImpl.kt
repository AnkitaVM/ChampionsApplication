package com.example.championsapplication.data.repository

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository

class FakeChampionRepositoryImpl : ChampionsRepository {
    override suspend fun getChampions(): Result<List<Champion>> {
        return Result.Success(getChampionsList())
    }

    override suspend fun getChampionDetails(chId: String): Result<Champion> {
        return Result.Success(getChampion("1"))
    }

    private fun getChampion(num: String): Champion {
        return Champion(
            num,
            "key$num",
            "name1$num",
            "title1$num",
            "blurb1$num",
            ChampionImage("full1$num", "", "")
        )
    }

    private fun getChampionsList(): List<Champion> {
        val championsList = mutableListOf<Champion>()
        val championOne =
            Champion("1", "key1", "name1", "title1", "blurb1", ChampionImage("full1", "", ""))
        val championTwo =
            Champion("2", "key2", "name2", "title2", "blurb2", ChampionImage("full2", "", ""))
        championsList.add(championOne)
        championsList.add(championTwo)
        return championsList
    }

}