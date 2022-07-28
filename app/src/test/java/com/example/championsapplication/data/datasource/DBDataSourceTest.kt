package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.domain.model.ErrorTypeHandlerImpl
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.getChampionEntity
import com.example.championsapplication.getChampionsEntitiesList
import com.example.championsapplication.getChampionsList
import com.example.championsapplication.domain.mappers.ChampionEntityModelMapper
import com.example.championsapplication.domain.mappers.ChampionModelMapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DBDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dbDataSource: DBDataSource

    @RelaxedMockK
    private lateinit var championsDao: ChampionsDao

    @Before
    fun setUp() {
        dbDataSource =
            DBDataSource(championsDao, ChampionEntityModelMapper(), ChampionModelMapper(), ErrorTypeHandlerImpl())
    }

    @Test
    fun insertAllChampions_verifyDaoInsertMethodCalled_successMethodCalled() {

        runTest {
            val chList = getChampionsList()
            dbDataSource.insertAllChampions(chList)
            coVerify {
                championsDao.insertAllChampions(any())
            }

//            verify(championsDao, times(1)).insertAllChampions(Mockito.anyList());

//            val entities = argCaptor.allValues
//            assertEquals(chList.size, entities.size)
        }
    }

    @Test
    fun getAllChampions_verifyDaoInsertMethodCalled_successListReturned() {
        runTest {
            val passedList = getChampionsEntitiesList()
            coEvery { championsDao.getAllChampions() } returns passedList
            val retured = dbDataSource.getAllChampions()
            assertEquals(retured.data!!.size, passedList.size)
            val returnedList = retured.data!!
            for (i in passedList.indices) {
                assertEquals(returnedList[i].id, passedList[i].id)
                assertEquals(returnedList[i].name, passedList[i].name)
            }

        }
    }

    @Test
    fun getAllChampions_verifyDaoInsertMethodCalled_emptyListReturnedFromDb() {
        runTest {
            val passedList = getChampionsEntitiesList()
            coEvery { championsDao.getAllChampions() } returns emptyList()
            val retured = dbDataSource.getAllChampions()
            assertTrue(retured is Result.Error)
        }
    }

    @Test
    fun getChampionDetails_verifyGetChampionDetailsDaoMethodCalled_success() {
        runTest {
            dbDataSource.getChampionDetails("1")
            coVerify { championsDao.getChampionDetails("1") }
        }
    }

    @Test
    fun getChampionDetails_verifyGetChampionDetailsDaoMethodCalled_successDetailsReturned() {
        runTest {
            val passedChampionEntity = getChampionEntity("1")
            coEvery { championsDao.getChampionDetails(any()) } returns passedChampionEntity
            val returnedChampion = dbDataSource.getChampionDetails("1")
            assertEquals(returnedChampion.data!!.id, passedChampionEntity.id)
            assertEquals(returnedChampion.data!!.name, passedChampionEntity.name)
            assertEquals(returnedChampion.data!!.title, passedChampionEntity.title)

        }
    }

    @Test
    fun getChampionDetails_nullReturnedFromGetChampionDetailsDaoMethod_ResultErrorReturned() {
        runTest {
            coEvery { championsDao.getChampionDetails(any()) } returns null
            val returned = dbDataSource.getChampionDetails("1")
            assertTrue(returned is Result.Error)
        }
    }

}