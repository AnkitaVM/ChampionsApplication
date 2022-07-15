package com.example.championsapplication.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.championsapplication.data.db.ChampionDatabase
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.getChampionsEntitiesList
import kotlinx.coroutines.runBlocking
import org.junit.*

class ChampionsDaoTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var championsDao: ChampionsDao
    private lateinit var championDatabase: ChampionDatabase

    @Before
    fun setup() {
        championDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ChampionDatabase::class.java
        ).allowMainThreadQueries().build()

        championsDao = championDatabase.getChampionDao()
    }

    @After
    fun tearDown() {
        championDatabase.close()
    }

    @Test
    fun insertAllChampions_championsInsertedinDb_success() {
        runBlocking {
            val listToInsert = getChampionsEntitiesList()
            championsDao.insertAllChampions(listToInsert)
            val returnedList = championsDao.getAllChampions()
            Assert.assertEquals(listToInsert, returnedList)
        }
    }

    @Test
    fun getChampionDetails_championsReturnedFromDb_success() {
        runBlocking {
            championsDao.insertAllChampions(getChampionsEntitiesList())
            val returneChampion = championsDao.getChampionDetails("2")
            val valueToVerify = getChampionsEntitiesList()[1]
            Assert.assertEquals(returneChampion, valueToVerify)
        }
    }

}