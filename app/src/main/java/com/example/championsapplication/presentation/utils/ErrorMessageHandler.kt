package com.example.championsapplication.presentation.utils

import android.content.Context
import com.example.championsapplication.R
import com.example.championsapplication.domain.model.ErrorType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMessageHandler @Inject constructor(@ApplicationContext private val context: Context) {
    fun getErrorMessage(errorType: ErrorType): String {
        return when (errorType) {
            is ErrorType.NetworkError -> context.getString(R.string.network_error_occurred)
            is ErrorType.ServerError -> context.getString(R.string.server_occurred)
            is ErrorType.DataError -> context.getString(R.string.data_error_occurred)
            is ErrorType.UnknownError -> context.getString(R.string.error_occurred)
        }
    }
}