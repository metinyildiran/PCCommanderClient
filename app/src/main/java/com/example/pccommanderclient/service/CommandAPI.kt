package com.example.pccommanderclient.service

import com.example.pccommanderclient.model.Command
import retrofit2.http.Body
import retrofit2.http.POST

interface CommandAPI {
    @POST("/command")
    suspend fun sendCommand(@Body command: Command)
}