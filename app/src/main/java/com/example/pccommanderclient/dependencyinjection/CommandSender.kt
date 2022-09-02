package com.example.pccommanderclient.dependencyinjection

import com.example.pccommanderclient.util.SentResult
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import javax.inject.Inject

@ViewModelScoped
class CommandSender @Inject constructor(private var socket: SentResult<Socket>) {

    fun sendCommand(command: String) {
        when (socket) {
            is SentResult.Success -> {
                try {
                    val dos = DataOutputStream(socket.data?.getOutputStream())
                    dos.writeUTF(command)
                    socket.data?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            is SentResult.Error -> {
                println(socket.message)
            }
        }
    }
}