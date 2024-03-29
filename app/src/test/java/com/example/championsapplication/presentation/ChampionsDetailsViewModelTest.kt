package com.example.championsapplication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.*
import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import com.example.championsapplication.presentation.viewmodels.ChampionsDetailsViewModel
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
class ChampionsDetailsViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var coroutineRule = MainDispatcherRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var getChampionDetailsUseCase: GetChampionDetailsUseCase

    private lateinit var championsDetailsViewModel: ChampionsDetailsViewModel

    @Before
    fun setUp() {
        championsDetailsViewModel =
            ChampionsDetailsViewModel(
                getChampionDetailsUseCase, coroutineRule.testDispatcher
            )
    }

    @Test
    fun getChampionDetails_championDetailsVM_successReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } returns getChampionDetailsResultSuccess()
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Success)
            Assert.assertEquals(champion.data, getUIChampionModel("1"))
        }
    }

    @Test
    fun getChampionDetails_championDetailsVM_DataErrorReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } returns getChampionDetailsResultDataError()
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Error)
            Assert.assertNull(champion.data)
            Assert.assertTrue(champion.errorType is ErrorType.DataError)
        }
    }

    @Test
    fun getChampionDetails_championDetailsVM_ServerErrorReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } returns getChampionDetailsResultServerError()
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Error)
            Assert.assertNull(champion.data)
            Assert.assertTrue(champion.errorType is ErrorType.ServerError)
        }
    }

    @Test
    fun getChampionDetails_championDetailsVM_NetworkErrorReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } returns getChampionDetailsResultNetworkError()
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Error)
            Assert.assertNull(champion.data)
            Assert.assertTrue(champion.errorType is ErrorType.NetworkError)
        }
    }

    @Test
    fun getChampionDetails_championDetailsVM_UnknownErrorReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } returns getChampionDetailsResultUnknownError()
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Error)
            Assert.assertNull(champion.data)
            Assert.assertTrue(champion.errorType is ErrorType.UnknownError)
        }
    }

    @Test
    fun getChampionDetails_exceptionThrown_UnknownErrorReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } throws Exception("Test Exception")
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Error)
            Assert.assertNull(champion.data)
            Assert.assertTrue(champion.errorType is ErrorType.UnknownError)
        }
    }
}