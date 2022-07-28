package com.example.championsapplication.data.datasource

import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.ErrorTypeHandler
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.mappers.ChampionEntityModelMapper
import com.example.championsapplication.domain.mappers.ChampionModelMapper
import javax.inject.Inject

class DBDataSource @Inject constructor(
    private var championsDao: ChampionsDao,
    private val championEntityModelMapper: ChampionEntityModelMapper,
    private val championModelMapper: ChampionModelMapper,
    private val errorTypeHandler: ErrorTypeHandler
) {
    suspend fun insertAllChampions(champions: List<Champion>) {
        championsDao.insertAllChampions(champions.map { champion ->
            championEntityModelMapper.map(champion)
        })
    }

    suspend fun getAllChampions(): Result<List<Champion>> {
        try {
            val chList = championsDao.getAllChampions()
                .map { championEntity ->
                    championModelMapper.map(championEntity)
                }
            if (chList.isNotEmpty()) {
                return Result.Success(chList)
            }
            return Result.Error(ErrorType.DataError)
        } catch (e: Exception) {
            return Result.Error(errorTypeHandler.getError(e))
        }
    }

    suspend fun getChampionDetails(id: String): Result<Champion> {
        try {
            val championEntity = championsDao.getChampionDetails(id)
            if (championEntity != null) {
                return Result.Success(championModelMapper.map(championEntity))
            }
            return Result.Error(ErrorType.DataError)
        } catch (e: Exception) {
            return Result.Error(errorTypeHandler.getError(e))
        }
    }
}