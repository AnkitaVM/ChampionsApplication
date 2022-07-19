package com.example.championsapplication.data.datasource

import com.example.championsapplication.getChampion
import com.example.championsapplication.getChampionsList
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = LocalDataSource()
    }

    @Test
    fun getChampionsDataFromLocalCache_dataPresentInCache_SuccessDataReturnedFromCache() {
        localDataSource.saveChampionsDataInLocalCache(getChampionsList())
        val champData = localDataSource.getChampionsDataFromLocalCache()
        Assert.assertEquals(champData.data, getChampionsList())
    }

}