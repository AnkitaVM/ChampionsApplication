package com.example.championsapplication.presentation.utils

import android.content.Context
import com.example.championsapplication.R
import com.example.championsapplication.domain.model.ErrorType
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ErrorMessageHandlerTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var errorMessageHandler: ErrorMessageHandler

    @MockK
    private lateinit var context: Context

    @Before
    fun setUp() {
        errorMessageHandler = ErrorMessageHandler(context)
    }

    @Test
    fun getErrorMessage_NetworkErrorPassed_NetworkErrorStringReturned() {
        val passedError = "Please check your internet connection"
        every { context.getString(R.string.network_error_occurred) } returns passedError
        val error = errorMessageHandler.getErrorMessage(ErrorType.NetworkError)
        Assert.assertEquals(error, passedError)
    }

    @Test
    fun getErrorMessage_DataErrorPassed_DataErrorStringReturned() {
        val passedError = "Some error occurred while fetching data. Please try again later."
        every { context.getString(R.string.data_error_occurred) } returns passedError
        val error = errorMessageHandler.getErrorMessage(ErrorType.DataError)
        Assert.assertEquals(error, passedError)
    }

    @Test
    fun getErrorMessage_ServerErrorPassed_ServerErrorStringReturned() {
        val passedError = "Unable to connect to server. Please try again later"
        every { context.getString(R.string.server_occurred) } returns passedError
        val error = errorMessageHandler.getErrorMessage(ErrorType.ServerError)
        Assert.assertEquals(error, passedError)
    }

    @Test
    fun getErrorMessage_UnknownErrorPassed_UnknownErrorStringReturned() {
        val passedError = "Unexpected error occurred"
        every { context.getString(R.string.error_occurred) } returns passedError
        val error = errorMessageHandler.getErrorMessage(ErrorType.UnknownError)
        Assert.assertEquals(error, passedError)
    }
}