package com.example.championsapplication.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.datasource.ApiDataSource
import com.example.championsapplication.data.datasource.DBDataSource
import com.example.championsapplication.data.datasource.LocalDataSource
import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getResponseInWrappedResultClassFromDB
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChampionRepositoryImplTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var championRepositoryImpl: ChampionRepositoryImpl

    @RelaxedMockK
    private lateinit var localDataSource: LocalDataSource

    @RelaxedMockK
    private lateinit var dbDataSource: DBDataSource

    @RelaxedMockK
    private lateinit var apiDataSource: ApiDataSource

    @Before
    fun setUp() {
        championRepositoryImpl =
            ChampionRepositoryImpl(localDataSource, apiDataSource, dbDataSource)
    }

    @Test
    fun getChampionsList_whenListIsSavedInLocalCache_ListReturnedFromLocalCache() {
        runTest {
            coEvery { localDataSource.getChampionsDataFromLocalCache() } returns getResponseInWrappedResultClassFromDB()
            val list = championRepositoryImpl.getChampions()
            Assert.assertEquals(
                list.data,
                getResponseInWrappedResultClassFromDB().data
            )
        }
    }

    @Test
    fun getChampionsList_whenListIsSavedInLocalCache_NoInteractioWithDbAndApiObject() {
        runTest {
            coEvery { localDataSource.getChampionsDataFromLocalCache() } returns getResponseInWrappedResultClassFromDB()
            championRepositoryImpl.getChampions()
            verify { dbDataSource wasNot Called }
            verify { apiDataSource wasNot Called }
        }
    }

    @Test
    fun getChampionsList_whenListIsSavedInDb_ListReturnedFromDb() {
        runTest {
            coEvery { localDataSource.getChampionsDataFromLocalCache() } returns Result.Error(
                ErrorType.DataError
            )
            coEvery { dbDataSource.getAllChampions() } returns getResponseInWrappedResultClassFromDB()
            val list = championRepositoryImpl.getChampions()
            Assert.assertEquals(
                list.data,
                getResponseInWrappedResultClassFromDB().data
            )
        }
    }

    @Test
    fun getChampionsList_whenListSavedInDb_NoInteractioWithApiObject() {
        runTest {
            coEvery { localDataSource.getChampionsDataFromLocalCache() } returns Result.Error(
                ErrorType.DataError
            )
            coEvery { dbDataSource.getAllChampions() } returns getResponseInWrappedResultClassFromDB()
            championRepositoryImpl.getChampions()
            verify { apiDataSource wasNot Called }
        }
    }

    @Test
    fun getChampionsList_whenNoListSavedInLocalAndDb_ListReturnedFromApi() {
        runTest {
            coEvery { localDataSource.getChampionsDataFromLocalCache() } returns Result.Error(
                ErrorType.DataError
            )
            coEvery { dbDataSource.getAllChampions() } returns Result.Error(ErrorType.DataError)
            coEvery { apiDataSource.callChampionsService() } returns getResponseInWrappedResultClass()
            val list = championRepositoryImpl.getChampions()
            Assert.assertEquals(
                list.data,
                getResponseInWrappedResultClass().data
            )
        }
    }

    @Test
    fun getChampionDetails_IdPassed_dbDatasourceMethodCalled() {
        runTest {
            championRepositoryImpl.getChampionDetails("1")
            coVerify { dbDataSource.getChampionDetails("1") }
        }
    }
}