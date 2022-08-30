package com.example.pccommanderclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pccommanderclient.ui.theme.PCCommanderClientTheme
import java.io.DataOutputStream
import java.net.Socket

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PCCommanderClientTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val socket = Socket("192.168.1.35", 1755)

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Start Chrome")
        }
    }
}

private fun sendCommand(socket: Socket, command: String) {
    val thread = Thread {
        try {
            val DOS = DataOutputStream(socket.getOutputStream())
            DOS.writeUTF(command)
//                    socket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    thread.start()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PCCommanderClientTheme {
        MainScreen()
    }
}