package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.api.ApiCall
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.domain.model.ChampionListResponse
import com.example.championsapplication.domain.model.ErrorTypeHandlerImpl
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.getErrorMockResponse
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getSuccessMockResponse
import com.example.championsapplication.getSuccessMockResponseWrappedInResult
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ApiDataSourceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiDataSource: ApiDataSource

    @RelaxedMockK
    private lateinit var championsApi: ChampionsApi

    private lateinit var apiCall: ApiCall


    @Before
    fun setUp() {
        apiCall = ApiCall(ErrorTypeHandlerImpl())
        apiDataSource =
            ApiDataSource(championsApi, apiCall, ErrorTypeHandlerImpl())
    }

    @Test
    fun testCallChampionsService_serviceCalled_SuccessResponseReturned() {
        runTest {
            coEvery { championsApi.getChampionsList() } returns getSuccessMockResponse()
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