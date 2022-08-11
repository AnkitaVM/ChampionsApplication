package com.example.championsapplication.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.getChampionDetailsResultDataError
import com.example.championsapplication.getChampionsList
import com.example.championsapplication.getResponseInWrappedResultClassFromDB
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAllChampionsUseCaseTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var repository: ChampionsRepository

//    @MockK
//    private lateinit var listMapper: ListMapper<Champion, UIChampion>

    private lateinit var getAllChampionsUseCase: GetAllChampionsUseCase

//    @MockK
//    private lateinit var listChampion: List<Champion>


    @Before
    fun setUp() {
        getAllChampionsUseCase = GetAllChampionsUseCase(
            repository
//            ,listMapper
        )
    }

    @Test
    fun getAllChampions_GetAllChampionsUseCase_successUIChampionsListReturned() {
        runTest {
            coEvery { repository.getChampions() } returns getResponseInWrappedResultClassFromDB()
//            coEvery { listMapper.map(any()) } returns getUIChampionsList()

//            val s = mockkStatic("com.example.championsapplication.domain.mappers.ModelMapperKt")
//            coEvery { List<Champion>.toListOfUIChampion() } returns getUIChampionsList()
            val champions = getAllChampionsUseCase()
            Assert.assertTrue(champions is Result.Success)
            Assert.assertEquals(champions.data, getChampionsList())
        }
    }

    @Test
    fun getAllChampions_GetAllChampionsUseCase_ErrorReturned() {
        runTest {
            coEvery { repository.getChampions() } returns getChampionDetailsResultDataError()
//            coEvery { listMapper.map(any()) } returns getUIChampionsList()
            val champions = getAllChampionsUseCase()
            Assert.assertTrue(champions is Result.Error)
            Assert.assertNull(champions.data)
        }
    }

}