package com.example.championsapplication.data.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class ChampionsApiTest {
    private lateinit var championsApi: ChampionsApi
    private lateinit var mockWebserver: MockWebServer

    @Before
    fun setup() {
        mockWebserver = MockWebServer()
        championsApi = Retrofit.Builder().baseUrl(mockWebserver.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ChampionsApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebserver.shutdown()
    }

    private fun enqueueMockResponse() {
        val mockResponse = MockResponse()
        mockResponse.setBody(getMockResponse())
        mockWebserver.enqueue(mockResponse)
    }

    @Test
    fun getChampionsList_sendCorrectRequest_SuccessCorrectUrlCalled() {
        runTest {
            enqueueMockResponse()
            championsApi.getChampionsList()
            val request = mockWebserver.takeRequest()
            Assert.assertEquals(request.path, CHAMPIONS_LIST)
        }
    }

    @Test
    fun getChampionsList_sendRequest_SuccessResponseReceived() {
        runTest {
            enqueueMockResponse()
            val response = championsApi.getChampionsList()
            val request = mockWebserver.takeRequest()
            Assert.assertEquals(request.path, CHAMPIONS_LIST)
            Assert.assertTrue(response.isSuccessful)
            Assert.assertNotNull(response)
        }
    }

    @Test
    fun getChampionsList_sendRequest_ErrorResponseReceived() {
        runTest {
            enqueueErrorMockResponse()
            val response = championsApi.getChampionsList()
            val request = mockWebserver.takeRequest()
            Assert.assertFalse(response.isSuccessful)
        }
    }

    private fun enqueueErrorMockResponse() {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(401)
        mockResponse.setBody("{}")
        mockWebserver.enqueue(mockResponse)
    }

    // Helper

    private fun getMockResponse(): String {
        return "{\"type\":\"champion\",\"format\":\"standAloneComplex\",\"version\":\"12.9.1\",\"data\":{\"Aatrox\":{\"version\":\"12.9.1\",\"id\":\"Aatrox\",\"key\":\"266\",\"name\":\"Aatrox\",\"title\":\"the Darkin Blade\",\"blurb\":\"Once honored defenders of Shurima against the Void, Aatrox and his brethren would eventually become an even greater threat to Runeterra, and were defeated only by cunning mortal sorcery. But after centuries of imprisonment, Aatrox was the first to find...\",\"info\":{\"attack\":8,\"defense\":4,\"magic\":3,\"difficulty\":4},\"image\":{\"full\":\"Aatrox.png\",\"sprite\":\"champion0.png\",\"group\":\"champion\",\"x\":0,\"y\":0,\"w\":48,\"h\":48},\"tags\":[\"Fighter\",\"Tank\"],\"partype\":\"Blood Well\",\"stats\":{\"hp\":580,\"hpperlevel\":90,\"mp\":0,\"mpperlevel\":0,\"movespeed\":345,\"armor\":38,\"armorperlevel\":3.25,\"spellblock\":32,\"spellblockperlevel\":1.25,\"attackrange\":175,\"hpregen\":3,\"hpregenperlevel\":1,\"mpregen\":0,\"mpregenperlevel\":0,\"crit\":0,\"critperlevel\":0,\"attackdamage\":60,\"attackdamageperlevel\":5,\"attackspeedperlevel\":2.5,\"attackspeed\":0.651}}}}"
    }

}