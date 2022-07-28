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
    var coroutineRule = MainCoroutineScopeRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var getChampionDetailsUseCase: GetChampionDetailsUseCase

    private lateinit var championsDetailsViewModel: ChampionsDetailsViewModel

    @Before
    fun setUp() {
        championsDetailsViewModel =
            ChampionsDetailsViewModel(
                getChampionDetailsUseCase, coroutineRule.dispatcher
            )
    }

    @Test
    fun getChampionDetails_championDetailsVM_successReturned() {
        runTest {
            coEvery { getChampionDetailsUseCase("1") } returns getUIChampionDetailsResultSuccess()
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
            coEvery { getChampionDetailsUseCase("1") } returns getUIChampionDetailsResultDataError()
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
            coEvery { getChampionDetailsUseCase("1") } returns getUIChampionDetailsResultServerError()
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
            coEvery { getChampionDetailsUseCase("1") } returns getUIChampionDetailsResultNetworkError()
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
            coEvery { getChampionDetailsUseCase("1") } returns getUIChampionDetailsResultUnknownError()
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Error)
            Assert.assertNull(champion.data)
            Assert.assertTrue(champion.errorType is ErrorType.UnknownError)
        }
    }
}