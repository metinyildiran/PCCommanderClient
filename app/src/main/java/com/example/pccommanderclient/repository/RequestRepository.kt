package com.example.pccommanderclient.repository

import com.example.pccommanderclient.model.Request
import com.example.pccommanderclient.service.RequestAPI
import com.example.pccommanderclient.util.SentResult
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RequestRepository @Inject constructor(private val api: RequestAPI) {
    suspend fun sendCommand(request: Request): SentResult<Request> {
        return try {
            api.sendCommand(request)

            SentResult.Success(request)
        } catch (e: Exception) {
            SentResult.Error(e.message!!)
        }
    }

    suspend fun sendText(request: Request): SentResult<Request> {
        return try {
            api.sendText(request)

            SentResult.Success(request)
        } catch (e: Exception) {
            SentResult.Error(e.message!!)
        }
    }

    suspend fun sendMediaKeys(request: Request): SentResult<Request> {
        return try {
            api.sendMediaKeys(request)

            SentResult.Success(request)
        } catch (e: Exception) {
            SentResult.Error(e.message!!)
        }
    }
}