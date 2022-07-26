package com.example.championsapplication.data.datasource

import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import javax.inject.Inject

class DBDataSource @Inject constructor(
    private var championsDao: ChampionsDao
) {
    suspend fun insertAllChampions(champions: List<Champion>) {
        championsDao.insertAllChampions(champions.map { champion ->
            ChampionEntity.fromChampionModel(champion)
        })
    }

    suspend fun getAllChampions(): Result<List<Champion>> {
        try {
            val chList = championsDao.getAllChampions()
                .map { championEntity -> championEntity.toChampionModel() }
            if (chList.isNotEmpty()) {
                return Result.Success(chList)
            }
            return Result.Error("Error Occurred")
        } catch (e: Exception) {
            return Result.Error(e.message)
        }
    }

    suspend fun getChampionDetails(id: String): Result<Champion> {
        try {
            val championEntity = championsDao.getChampionDetails(id)
            if (championEntity != null) {
                return Result.Success(championEntity.toChampionModel())
            }
            return Result.Error("Error Occurred")
        } catch (e: Exception) {
            return Result.Error(e.message)
        }
    }
}