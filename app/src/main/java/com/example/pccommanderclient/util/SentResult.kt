package com.example.pccommanderclient.util

sealed class SentResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : SentResult<T>(data)
    class Error<T>(message: String, data: T? = null) : SentResult<T>(data, message)
}