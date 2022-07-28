package com.example.championsapplication.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getUIChampionDetailsResultDataError
import com.example.championsapplication.getUIChampionsList
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.domain.mappers.ListMapper
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

    @MockK
    private lateinit var listMapper: ListMapper<Champion, UIChampion>

    private lateinit var getAllChampionsUseCase: GetAllChampionsUseCase

    @Before
    fun setUp() {
        getAllChampionsUseCase = GetAllChampionsUseCase(repository, listMapper)
    }

    @Test
    fun getAllChampions_GetAllChampionsUseCase_successUIChampionsListReturned() {
        runTest {
            coEvery { repository.getChampions() } returns getResponseInWrappedResultClass()
            coEvery { listMapper.map(any()) } returns getUIChampionsList()
            val champions = getAllChampionsUseCase()
            Assert.assertTrue(champions is Result.Success)
            Assert.assertEquals(champions.data, getUIChampionsList())
        }
    }

    @Test
    fun getAllChampions_GetAllChampionsUseCase_ErrorReturned() {
        runTest {
            coEvery { repository.getChampions() } returns getUIChampionDetailsResultDataError()
//            coEvery { listMapper.map(any()) } returns getUIChampionsList()
            val champions = getAllChampionsUseCase()
            Assert.assertTrue(champions is Result.Error)
            Assert.assertNull(champions.data)
        }
    }

}