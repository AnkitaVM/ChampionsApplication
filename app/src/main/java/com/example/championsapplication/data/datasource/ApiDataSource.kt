package com.example.championsapplication.data.datasource

import com.example.championsapplication.data.api.ApiCall
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionListResponse
import com.example.championsapplication.domain.model.ErrorTypeHandler
import com.example.championsapplication.domain.model.Result
import retrofit2.Response
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private val championsApi: ChampionsApi,
    private val apiCall: ApiCall,
    private val errorTypeHandler: ErrorTypeHandler
) {

    suspend fun callChampionsService(): Result<List<Champion>> {
        val res = apiCall.makeApiCall { championsApi.getChampionsList() }
        if (res is Result.Success) {
            res.data?.let { return getChampionsFromResponse(it) }
        }
        return Result.Error(res.errorType)
    }

    private fun getChampionsFromResponse(response: ChampionListResponse): Result<List<Champion>> {
        return try {
            val ch = mutableListOf<Champion>()
            ch.addAll(response.championsMap.values)
            Result.Success(ch)
        } catch (e: Exception) {
            Result.Error(errorTypeHandler.getError(e))
        }
    }
}