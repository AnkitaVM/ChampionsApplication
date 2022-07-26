package com.example.championsapplication.presentation


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.MainDispatcherRule
import com.example.championsapplication.data.repository.FakeChampionRepositoryImpl
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import com.example.championsapplication.getOrAwaitValue
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.presentation.uimodels.UIChampionImage
import com.example.championsapplication.utils.ChampionImageUIModelMapper
import com.example.championsapplication.utils.ChampionUiModelMapper
import com.example.championsapplication.utils.ListMapperImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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


    private lateinit var championsListViewModel: ChampionsListViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val repositoryImpl = FakeChampionRepositoryImpl()
        championsListViewModel = ChampionsListViewModel(
            GetAllChampionsUseCase(
                repositoryImpl,
                ListMapperImpl(ChampionUiModelMapper(ChampionImageUIModelMapper()))
            ), mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun getAllChampions_championListViewModel_valueStoredInField() {

        runTest {
            championsListViewModel.getAllChampions()
            advanceUntilIdle()
            val champions = championsListViewModel.champions.getOrAwaitValue()
            Assert.assertTrue(champions is Result.Success)
            Assert.assertEquals(champions.data, getChampionsList())
        }

    }

    /* Helper */
    private fun getChampionsList(): List<UIChampion> {
        val championsList = mutableListOf<UIChampion>()
        val championOne =
            UIChampion("1", "key1", "name1", "title1", "blurb1", UIChampionImage("full1", "", ""))
        val championTwo =
            UIChampion("2", "key2", "name2", "title2", "blurb2", UIChampionImage("full2", "", ""))
        championsList.add(championOne)
        championsList.add(championTwo)
        return championsList
    }
}