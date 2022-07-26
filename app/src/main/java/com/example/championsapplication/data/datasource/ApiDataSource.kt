package com.example.championsapplication.data.datasource

import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionListResponse
import com.example.championsapplication.domain.model.Result
import retrofit2.Response
import javax.inject.Inject

class ApiDataSource @Inject constructor(
    private var championsApi: ChampionsApi
) {

    suspend fun callChampionsService(): Result<List<Champion>> {
        val res = championsApi.getChampionsList()
        return getChampionsFromResponse(res)
    }

    private fun getChampionsFromResponse(response: Response<ChampionListResponse>): Result<List<Champion>> {
        try {
            if (response.isSuccessful) {
                response.body()?.let { apiRes ->
                    val ch = mutableListOf<Champion>()
                    ch.addAll(apiRes.championsMap.values)
                    return Result.Success(ch)
                }
            }
            return Result.Error(response.message())
        } catch (e: Exception) {
            return Result.Error(e.message)
        }
    }
}