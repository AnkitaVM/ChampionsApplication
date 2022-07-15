package com.example.championsapplication.data.repository

import com.example.championsapplication.data.datasource.ApiDataSource
import com.example.championsapplication.data.datasource.DBDataSource
import com.example.championsapplication.data.model.Champion
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import javax.inject.Inject

class ChampionRepositoryImpl @Inject constructor(
    private var apiDataSource: ApiDataSource,
    private var dbDataSource: DBDataSource
) :
    ChampionsRepository {
    override suspend fun getChampions(): Result<List<Champion>> {
        return getChampionsList()
    }

    override suspend fun getChampionDetails(chId: String): Result<Champion> {
        return dbDataSource.getChampionDetails(chId)
    }

    suspend fun getChampionsList(): Result<List<Champion>> {

        var champions = dbDataSource.getAllChampions()
        if (champions.data == null || champions.data!!.isEmpty()) {
            champions = apiDataSource.callChampionsService()
            champions.data?.let { dbDataSource.insertAllChampions(it) }
        }
        return champions
    }
}