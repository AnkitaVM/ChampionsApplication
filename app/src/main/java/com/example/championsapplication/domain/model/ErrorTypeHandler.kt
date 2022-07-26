package com.example.championsapplication.domain.model

interface ErrorTypeHandler {
    fun getError(exception: Exception): ErrorType
}