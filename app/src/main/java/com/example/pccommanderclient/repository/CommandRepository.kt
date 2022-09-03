package com.example.pccommanderclient.repository

import com.example.pccommanderclient.model.Command
import com.example.pccommanderclient.service.CommandAPI
import com.example.pccommanderclient.util.SentResult
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CommandRepository @Inject constructor(private val api: CommandAPI) {
    suspend fun sendCommand(command: Command): SentResult<Command> {
        return try {
            api.sendCommand(command)

            SentResult.Success(command)
        } catch (e: Exception) {
            SentResult.Error(e.message!!)
        }
    }
}