package com.example.pccommanderclient.dependencyinjection

import dagger.hilt.android.scopes.ViewModelScoped
import java.io.DataOutputStream
import java.net.Socket
import javax.inject.Inject

@ViewModelScoped
class CommandSender @Inject constructor(var socket: Socket) {

    fun sendCommand(command: String) {
        val dos = DataOutputStream(socket.getOutputStream())
        dos.writeUTF(command)
    }
}