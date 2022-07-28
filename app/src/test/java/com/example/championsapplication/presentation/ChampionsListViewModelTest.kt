package com.example.championsapplication.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.*
import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import com.example.championsapplication.presentation.viewmodels.ChampionsListViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChampionsListViewModelTest {

    @get : Rule
    var mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getAllChampionsUseCase: GetAllChampionsUseCase

    private lateinit var championsListViewModel: ChampionsListViewModel

    @Before
    fun setUp() {
        championsListViewModel = ChampionsListViewModel(
            getAllChampionsUseCase, mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun getAllChampions_championListViewModel_valueStoredInField() {
        runTest {
            coEvery { getAllChampionsUseCase() } returns getUIChampionListResultSuccess()
            championsListViewModel.getAllChampions()
            advanceUntilIdle()
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Success)
            Assert.assertEquals(champions.data, getUIChampionsList())
        }
    }

    @Test
    fun getAllChampions_championListViewModel_DataErrorReturned() {
        runTest {
            coEvery { getAllChampionsUseCase() } returns getUIChampionDetailsResultDataError()
            championsListViewModel.getAllChampions()
            advanceUntilIdle()
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Error)
            Assert.assertNull(champions.data)
            Assert.assertTrue(champions.errorType is ErrorType.DataError)
        }
    }

    @Test
    fun getAllChampions_championListViewModel_ServerErrorReturned() {
        runTest {
            coEvery { getAllChampionsUseCase() } returns getUIChampionDetailsResultServerError()
            championsListViewModel.getAllChampions()
            advanceUntilIdle()
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Error)
            Assert.assertNull(champions.data)
            Assert.assertTrue(champions.errorType is ErrorType.ServerError)
        }
    }

    @Test
    fun getAllChampions_championListViewModel_NetworkErrorReturned() {
        runTest {
            coEvery { getAllChampionsUseCase() } returns getUIChampionDetailsResultNetworkError()
            championsListViewModel.getAllChampions()
            advanceUntilIdle()
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Error)
            Assert.assertNull(champions.data)
            Assert.assertTrue(champions.errorType is ErrorType.NetworkError)
        }
    }

    @Test
    fun getAllChampions_championListViewModel_UnknownErrorReturned() {
        runTest {
            coEvery { getAllChampionsUseCase() } returns getUIChampionDetailsResultUnknownError()
            championsListViewModel.getAllChampions()
            advanceUntilIdle()
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Error)
            Assert.assertNull(champions.data)
            Assert.assertTrue(champions.errorType is ErrorType.UnknownError)
        }
    }
}