package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.getErrorMockResponse
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getSuccessMockResponse
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
class ApiDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiDataSource: ApiDataSource

    @MockK
    private lateinit var championsApi: ChampionsApi


    @Before
    fun setUp() {
        apiDataSource = ApiDataSource(championsApi)
    }

    @Test
    fun testCallChampionsService_serviceCalled_SuccessResponseReturned() {
        runTest {
            coEvery { championsApi.getChampionsList() } returns getSuccessMockResponse()
//            Mockito.`when`(championsApi.getChampionsList()).thenReturn(getSuccessMockResponse())
            val resultList = apiDataSource.callChampionsService()
            Assert.assertEquals(
                resultList.data,
                getResponseInWrappedResultClass().data
            )
        }

    }

    @Test
    fun getChampionsFromResponse_serviceResponseUnsuccessful_ErrorReturned() {
        runTest {
            coEvery { championsApi.getChampionsList() } returns getErrorMockResponse()
            val resultList = apiDataSource.callChampionsService()
            Assert.assertNull(resultList.data)
            Assert.assertTrue(resultList is Result.Error)
        }

    }


}