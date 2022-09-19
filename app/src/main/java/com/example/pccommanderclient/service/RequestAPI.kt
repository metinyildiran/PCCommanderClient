package com.example.pccommanderclient.service

import com.example.pccommanderclient.model.Request
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestAPI {
    @POST("/command")
    suspend fun sendCommand(@Body request: Request)

    @POST("/media")
    suspend fun sendMediaKeys(@Body request: Request)
}