package com.example.championsapplication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.MainCoroutineScopeRule
import com.example.championsapplication.data.repository.FakeChampionRepositoryImpl
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import com.example.championsapplication.getOrAwaitValue
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.presentation.uimodels.UIChampionImage
import com.example.championsapplication.utils.ChampionImageUIModelMapper
import com.example.championsapplication.utils.ChampionUiModelMapper
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

    private lateinit var championsDetailsViewModel: ChampionsDetailsViewModel
    private lateinit var championUiModelMapper: ChampionUiModelMapper

    @Before
    fun setUp() {
        val repositoryImpl = FakeChampionRepositoryImpl()
        championUiModelMapper = ChampionUiModelMapper(ChampionImageUIModelMapper())
        championsDetailsViewModel =
            ChampionsDetailsViewModel(
                GetChampionDetailsUseCase(
                    repositoryImpl,
                    championUiModelMapper
                ), coroutineRule.dispatcher
            )
    }

    @Test
    fun getChampionDetails_championDetailsVM_successReturned() {
        runTest {
            championsDetailsViewModel.getChampionDetails("1")
            advanceUntilIdle()
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Success)
            Assert.assertEquals(champion.data, getChampion("1"))
        }
    }

    // Helper
    private fun getChampion(num: String): UIChampion {
        return UIChampion(
            num,
            "key$num",
            "name1$num",
            "title1$num",
            "blurb1$num",
            UIChampionImage("full1$num", "", "")
        )
    }
}