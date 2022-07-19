package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.domain.model.ChampionListResponse
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getSuccessMockResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ApiDataSourceTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiDataSource: ApiDataSource

    @Mock
    private lateinit var championsApi: ChampionsApi


    @Before
    fun setUp() {
        apiDataSource = ApiDataSource(championsApi)
    }

    @Test
    fun testCallChampionsService_serviceCalled_SuccessResponseReturned() {
        runTest {
            Mockito.`when`(championsApi.getChampionsList()).thenReturn(getSuccessMockResponse())
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
            val resultList = apiDataSource.callChampionsService()
            Assert.assertNull(resultList.data)
            Assert.assertTrue(resultList is Result.Error)
        }

    }


}