package com.example.championsapplication.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.datasource.ApiDataSource
import com.example.championsapplication.data.datasource.ApiDataSourceTest
import com.example.championsapplication.data.datasource.DBDataSource
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getResponseInWrappedResultClassFromDB
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ChampionRepositoryImplTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var championRepositoryImpl: ChampionRepositoryImpl

    @Mock
    private lateinit var dbDataSource: DBDataSource

    @Mock
    private lateinit var apiDataSource: ApiDataSource

    @Before
    fun setUp() {
        championRepositoryImpl = ChampionRepositoryImpl(apiDataSource, dbDataSource)
    }


    @Test
    fun getChampionsList_whenListIsSavedInDb_ListReturnedFromDb() {
        runTest {
            Mockito.`when`(dbDataSource.getAllChampions())
                .thenReturn(getResponseInWrappedResultClassFromDB())
            val list = championRepositoryImpl.getChampions()
            MatcherAssert.assertThat(
                list.data,
                CoreMatchers.`is`(getResponseInWrappedResultClassFromDB().data)
            )
        }

    }

    @Test
    fun getChampionsList_whenListSavedInDb_NoInteractioWithApiObject() {
        runTest {
            Mockito.`when`(dbDataSource.getAllChampions())
                .thenReturn(getResponseInWrappedResultClassFromDB())
            val list = championRepositoryImpl.getChampions()
            verifyNoInteractions(apiDataSource);
        }
    }

    @Test
    fun getChampionsList_whenNoListSavedInDb_ListReturnedFromApi() {
        runTest {
            Mockito.`when`(dbDataSource.getAllChampions())
                .thenReturn(Result.Error("No Data in Db"))
            Mockito.`when`(apiDataSource.callChampionsService())
                .thenReturn(getResponseInWrappedResultClass())
            val list = championRepositoryImpl.getChampions()
            MatcherAssert.assertThat(
                list.data,
                CoreMatchers.`is`(getResponseInWrappedResultClass().data)
            )
        }
    }

    @Test
    fun getChampionDetails_IdPassed_dbDatasourceMethodCalled() {
        runTest {
            championRepositoryImpl.getChampionDetails("1")
            verify(dbDataSource, times(1)).getChampionDetails("1");
        }
    }
}
