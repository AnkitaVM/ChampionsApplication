package com.example.championsapplication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.data.repository.FakeChampionRepositoryImpl
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import com.example.championsapplication.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChampionsDetailsViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var championsDetailsViewModel: ChampionsDetailsViewModel

    @Before
    fun setUp() {
        val repositoryImpl = FakeChampionRepositoryImpl()
        championsDetailsViewModel =
            ChampionsDetailsViewModel(GetChampionDetailsUseCase(repositoryImpl))
    }

    @Test
    fun getChampionDetails_championDetailsVM_successReturned() {
        runTest {
            championsDetailsViewModel.getChampionDetails("1")
            delay(2)
            val champion = championsDetailsViewModel.champion.getOrAwaitValue()
            Assert.assertTrue(champion is Result.Success)
            Assert.assertEquals(champion.data, getChampion("1"))
        }
    }

    // Helper

    private fun getChampion(num: String): Champion {
        return Champion(
            num,
            "key$num",
            "name1$num",
            "title1$num",
            "blurb1$num",
            ChampionImage("full1$num", "", "")
        )
    }
}

