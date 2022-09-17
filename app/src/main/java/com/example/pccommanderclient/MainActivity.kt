package com.example.pccommanderclient

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pccommanderclient.model.Command
import com.example.pccommanderclient.ui.theme.PCCommanderClientTheme
import com.example.pccommanderclient.viewmodel.CommandViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PCCommanderClientTheme {
                val viewModel: CommandViewModel = hiltViewModel()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(modifier = Modifier.width(200.dp), onClick = {
                        viewModel.sendCommand(Command("cmd /c start chrome https://www.youtube.com/feed/subscriptions"))

                    }) {
                        Text(text = "Start Youtube")
                    }

                    Button(modifier = Modifier.width(200.dp), onClick = {
                        viewModel.sendCommand(Command("rundll32.exe powrprof.dll SetSuspendState Sleep"))
                    }) {
                        Text(text = "Hibernate")
                    }

                    Button(onClick = { shareData() }) {
                        Text(text = "Send Text")
                    }
                }

                when {
                    intent?.action == Intent.ACTION_SEND -> {
                        if ("text/plain" == intent.type) {
                            handleSendText(viewModel, intent)
                        } else if (intent.type?.startsWith("image/") == true) {
                            handleSendImage(intent)
                        }
                    }
                    intent?.action == Intent.ACTION_SEND_MULTIPLE
                            && intent.type?.startsWith("image/") == true -> {
                        handleSendMultipleImages(intent)
                    }
                    else -> {
                        // Handle other intents, such as being started from the home screen
                    }
                }
            }
        }
    }

    private fun handleSendText(viewModel: CommandViewModel, intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
            viewModel.sendCommand(Command("cmd /c start chrome $incomingText"))
            finishAffinity()
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let { incomingImage ->

        }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)
            ?.let { incomingMultipleImages ->

            }
    }

    private fun shareData() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
