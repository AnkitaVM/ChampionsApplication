package com.example.championsapplication.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.data.repository.FakeChampionRepositoryImpl
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import com.example.championsapplication.getOrAwaitValue
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        championsDetailsViewModel.getChampionDetails("1")
        val champion = championsDetailsViewModel.champion.getOrAwaitValue()
        MatcherAssert.assertThat(champion.data, CoreMatchers.`is`(getChampion("1")))
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

