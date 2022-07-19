package com.example.championsapplication.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.MainCoroutineRule
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.data.repository.FakeChampionRepositoryImpl
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import com.example.championsapplication.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChampionsListViewModelTest {


    @get: Rule
    var mainCoroutineScopeRule = MainCoroutineRule()

    private lateinit var championsListViewModel: ChampionsListViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val repositoryImpl = FakeChampionRepositoryImpl()
        championsListViewModel = ChampionsListViewModel(GetAllChampionsUseCase(repositoryImpl))
    }

    @Test
    fun getAllChampions_championListViewModel_valueStoredInField() {

        runTest {
            championsListViewModel.getAllChampions()
            val championsLoading = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(championsLoading is Result.Loading)
            delay(2)
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Success)
            Assert.assertEquals(champions.data, getChampionsList())
        }

    }

    /* Helper */
    private fun getChampionsList(): List<Champion> {
        val championsList = mutableListOf<Champion>()
        val championOne =
            Champion("1", "key1", "name1", "title1", "blurb1", ChampionImage("full1", "", ""))
        val championTwo =
            Champion("2", "key2", "name2", "title2", "blurb2", ChampionImage("full2", "", ""))
        championsList.add(championOne)
        championsList.add(championTwo)
        return championsList
    }
}