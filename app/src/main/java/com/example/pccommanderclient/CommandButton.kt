package com.example.pccommanderclient

import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.DataOutputStream
import java.net.Socket

@Preview
@Composable
fun CommandButton(text: String = "Send Command", command: String = "") {
    Button(modifier = Modifier.width(200.dp), onClick = { sendCommand(command) }) {
        Text(text = text)
    }
}

private fun sendCommand(command: String) {
    val thread = Thread {
        try {
            val socket = Socket("192.168.1.35", 1755)
            val dos = DataOutputStream(socket.getOutputStream())
            dos.writeUTF(command)
            socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    thread.start()
}