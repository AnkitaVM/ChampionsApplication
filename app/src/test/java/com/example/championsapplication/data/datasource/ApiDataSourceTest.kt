package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.*
import com.example.championsapplication.data.api.ApiCall
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.domain.model.ChampionListResponse
import com.example.championsapplication.domain.model.ErrorTypeHandlerImpl
import com.example.championsapplication.domain.model.Result
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit4.MockKRule
import io.mockk.spyk
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

    @MockK
    private lateinit var championsApi: ChampionsApi

    @MockK
    private lateinit var errorTypeHandlerImpl: ErrorTypeHandlerImpl

    private lateinit var apiCall: ApiCall
    private lateinit var apiDataSource: ApiDataSource

    @Before
    fun setUp() {
        apiCall = ApiCall(errorTypeHandlerImpl)
        apiDataSource = ApiDataSource(championsApi, apiCall, errorTypeHandlerImpl)
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