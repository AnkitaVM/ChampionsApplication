package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.getChampionEntity
import com.example.championsapplication.getChampionsEntitiesList
import com.example.championsapplication.getChampionsList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DBDataSourceTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dbDataSource: DBDataSource

    @Mock
    private lateinit var championsDao: ChampionsDao

    @Captor
    private lateinit var argCaptor: ArgumentCaptor<List<ChampionEntity>>

    @Before
    fun setUp() {
        dbDataSource = DBDataSource(championsDao)
    }

    @Test
    fun insertAllChampions_verifyDaoInsertMethodCalled_successMethodCalled() {

        runTest {
            val chList = getChampionsList()
            dbDataSource.insertAllChampions(chList)
            verify(championsDao, times(1)).insertAllChampions(Mockito.anyList());

//            val entities = argCaptor.allValues
//            assertEquals(chList.size, entities.size)
        }
    }

    @Test
    fun getAllChampions_verifyDaoInsertMethodCalled_successListReturned() {
        runTest {
            val passedList = getChampionsEntitiesList()
            Mockito.`when`(championsDao.getAllChampions()).thenReturn(passedList)
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
            Mockito.`when`(championsDao.getAllChampions()).thenReturn(emptyList())
            val retured = dbDataSource.getAllChampions()
            assertTrue(retured is Result.Error)
        }
    }

    @Test
    fun getChampionDetails_verifyGetChampionDetailsDaoMethodCalled_success() {
        runTest {
            dbDataSource.getChampionDetails("1")
            verify(championsDao, times(1)).getChampionDetails("1");

        }
    }

    @Test
    fun getChampionDetails_verifyGetChampionDetailsDaoMethodCalled_successDetailsReturned() {
        runTest {
            val passedChampionEntity = getChampionEntity("1")
            Mockito.`when`(championsDao.getChampionDetails(Mockito.anyString()))
                .thenReturn(passedChampionEntity)
            val returnedChampion = dbDataSource.getChampionDetails("1")
            assertEquals(returnedChampion.data!!.id, passedChampionEntity.id)
            assertEquals(returnedChampion.data!!.name, passedChampionEntity.name)
            assertEquals(returnedChampion.data!!.title, passedChampionEntity.title)

        }
    }

    @Test
    fun getChampionDetails_nullReturnedFromGetChampionDetailsDaoMethod_ResultErrorReturned() {
        runTest {
            Mockito.`when`(championsDao.getChampionDetails(Mockito.anyString())).thenReturn(null)
            val returned = dbDataSource.getChampionDetails("1")
            assertTrue(returned is Result.Error)
        }
    }

}