package com.example.championsapplication.data.datasource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.data.model.ChampionListResponse
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.getResponseInWrappedResultClass
import com.example.championsapplication.getSuccessMockResponse
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ApiDataSourceTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiDataSource: ApiDataSource

    @Mock
    private lateinit var championsApi: ChampionsApi

    @Mock
    private lateinit var respone: Response<ChampionListResponse>

    @Before
    fun setUp() {
        apiDataSource = ApiDataSource(championsApi)
    }

    @Test
    fun testCallChampionsService_serviceCalled_SuccessResponseReturned() {
        runTest {
            Mockito.`when`(championsApi.getChampionsList()).thenReturn(getSuccessMockResponse())
            val resultList = apiDataSource.callChampionsService()
            MatcherAssert.assertThat(
                resultList.data,
                CoreMatchers.`is`(getResponseInWrappedResultClass().data)
            )
        }

    }

//    @Test
//    fun getChampionsFromResponse_serviceResponseUnsuccessful_ErrorReturned() {
//        runTest {
//            Mockito.`when`(respone.isSuccessful).thenReturn(false)
//            Mockito.`when`(respone.message()).thenReturn("test msg")
//            val resultList = apiDataSource.callChampionsService()
//            Assert.assertNull(resultList.data)
//            Assert.assertTrue(resultList is Result.Error)
//        }
//
//    }

//    @Test
//    fun testCallChampionsService_serviceCalled_ExceptionResponseReturned() {
//
//        runTest {
//            Mockito.`when`(championsApi.getChampionsList()).thenThrow(Exception())
//            val result = apiDataSource.callChampionsService()
//            MatcherAssert.assertThat(
//                result.message,
//                CoreMatchers.`is`(Exception().message)
//            )
//        }
//
//    }

}