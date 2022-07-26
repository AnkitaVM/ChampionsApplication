package com.example.championsapplication.data.api

import com.example.championsapplication.domain.model.*
import retrofit2.Response
import javax.inject.Inject

class ApiCall @Inject constructor(
    private val errorTypeHandler: ErrorTypeHandler
) {
    suspend fun <T : Any> makeApiCall(requestApi: suspend () -> Response<T>): Result<T> {
        return try {
            val response = requestApi.invoke()
            parseResponse(response)
        } catch (exception: Exception) {
            Result.Error(errorTypeHandler.getError(exception))
        }
    }

    private fun <T> parseResponse(response: Response<T>): Result<T> {
        try {
            if (response.isSuccessful) {
                response.body()?.let { apiRes ->
                    return Result.Success(apiRes)
                }
            }
            return Result.Error(ErrorType.ServerError)
        } catch (e: Exception) {
            return Result.Error(errorTypeHandler.getError(e))
        }
    }
}