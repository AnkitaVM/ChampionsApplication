package com.example.championsapplication.data.datasource

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import javax.inject.Inject

class LocalDataSource @Inject constructor() {
    private var championsLocal = mutableListOf<Champion>()

    fun getChampionsDataFromLocalCache(): Result<List<Champion>> {
        return Result.Success(championsLocal)
    }

    fun saveChampionsDataInLocalCache(championsLocalList: List<Champion>) {
        championsLocal.clear()
        championsLocal.addAll(championsLocalList)
    }
}