package com.example.championsapplication.domain.model

import java.net.UnknownHostException
import javax.inject.Inject

class ErrorTypeHandlerImpl @Inject constructor() : ErrorTypeHandler {
    override fun getError(exception: Exception): ErrorType {
        return when (exception) {
            is UnknownHostException -> {
                ErrorType.NetworkError
            }
            else -> {
                ErrorType.UnknownError
            }
        }
    }
}