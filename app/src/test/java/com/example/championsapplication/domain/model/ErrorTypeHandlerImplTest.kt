package com.example.championsapplication.domain.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ErrorTypeHandlerImplTest {

    private lateinit var errorTypeHandlerImpl: ErrorTypeHandlerImpl

    @Before
    fun setUp() {
        errorTypeHandlerImpl = ErrorTypeHandlerImpl()
    }

    @Test
    fun getError_UnknownHostExceptionPassed_NetworkErrorReturned() {
        val errorType = errorTypeHandlerImpl.getError(UnknownHostException())
        Assert.assertTrue(errorType is ErrorType.NetworkError)
    }

    @Test
    fun getError_AnyExceptionOtherThanUnknownHostPassed_UnknownErrorReturned() {
        val errorType = errorTypeHandlerImpl.getError(Exception())
        Assert.assertTrue(errorType is ErrorType.UnknownError)
    }
}