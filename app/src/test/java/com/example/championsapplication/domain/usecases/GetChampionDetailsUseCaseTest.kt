package com.example.championsapplication.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.*
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.utils.ChampionUiModelMapper
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetChampionDetailsUseCaseTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var repository: ChampionsRepository

    @MockK
    private lateinit var championUiModelMapper: ChampionUiModelMapper

    private lateinit var getChampionDetailsUseCase: GetChampionDetailsUseCase

    @Before
    fun setUp() {
        getChampionDetailsUseCase = GetChampionDetailsUseCase(repository, championUiModelMapper)
    }

    @Test
    fun getAllChampions_GetChampionsDetailsUseCase_successUIChampionsDetailsModelReturned() {
        runTest {
            coEvery { repository.getChampionDetails("1") } returns getChampionDetailsResultSuccess()
            coEvery { championUiModelMapper.map(any()) } returns getUIChampionModel("1")
            val champions = getChampionDetailsUseCase("1")
            assertTrue(champions is Result.Success)
            assertEquals(champions.data, getUIChampionModel("1"))
        }
    }

    @Test
    fun getAllChampions_GetAllChampionsUseCase_ErrorReturned() {
        runTest {
            coEvery { repository.getChampionDetails("1") } returns getUIChampionDetailsResultDataError()
            val champion = getChampionDetailsUseCase("1")
            assertTrue(champion is Result.Error)
            assertNull(champion.data)
        }
    }

}