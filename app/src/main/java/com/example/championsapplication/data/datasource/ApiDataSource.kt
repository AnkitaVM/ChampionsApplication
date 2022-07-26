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
//        return Result.Error(res.errorType)
//        try {
//            val res = championsApi.getChampionsList()
//            return getChampionsFromResponse(res)
//        } catch (e: Exception) {
//            return Result.getResultError(e.message)
//        }
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

//    private fun getChampionsFromResponse1(response: Response<ChampionListResponse>): Result<List<Champion>> {
//        try {
//            if (response.isSuccessful) {
//                response.body()?.let { apiRes ->
//                    val ch = mutableListOf<Champion>()
//                    ch.addAll(apiRes.championsMap.values)
//                    return Result.Success(ch)
////                    return Result.getResultSuccess(ch)
//                }
//            }
//            return Result.Error(response.message())
//        } catch (e: Exception) {
//            return Result.Error(e.message)
//        }
//    }
}