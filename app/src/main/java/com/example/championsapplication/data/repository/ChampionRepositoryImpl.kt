package com.example.championsapplication.data.repository

import com.example.championsapplication.data.datasource.ApiDataSource
import com.example.championsapplication.data.datasource.DBDataSource
import com.example.championsapplication.data.datasource.LocalDataSource
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import javax.inject.Inject

class ChampionRepositoryImpl @Inject constructor(
    private var localDataSource: LocalDataSource,
    private var apiDataSource: ApiDataSource,
    private var dbDataSource: DBDataSource,
) :
    ChampionsRepository {
    override suspend fun getChampions(): Result<List<Champion>> {
        return getChampionsList()
    }

    override suspend fun getChampionDetails(chId: String): Result<Champion> {
        return dbDataSource.getChampionDetails(chId)
    }

    suspend fun getChampionsList(): Result<List<Champion>> {
        //from local cache
        var champions = localDataSource.getChampionsDataFromLocalCache()
        if (champions.data.isNullOrEmpty()) {
            //from db
            champions = getChampionsDataFromDb()
            if (champions.data.isNullOrEmpty()) {
                champions = getChampionsDataFromApi()
            }
        }
        return champions
    }

    private suspend fun getChampionsDataFromDb(): Result<List<Champion>> {
        val dataFromDb = dbDataSource.getAllChampions()
        dataFromDb.data?.let { localDataSource.saveChampionsDataInLocalCache(it) }
        return dataFromDb
    }

    private suspend fun getChampionsDataFromApi(): Result<List<Champion>> {
        val dataFromApi = apiDataSource.callChampionsService()
        dataFromApi.data?.let {
            dbDataSource.insertAllChampions(it)
            localDataSource.saveChampionsDataInLocalCache(it)
        }
        return dataFromApi
    }
}