package com.example.pccommanderclient

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pccommanderclient.model.Command
import com.example.pccommanderclient.ui.theme.AppTheme
import com.example.pccommanderclient.util.Constants.URL_REGEX
import com.example.pccommanderclient.viewmodel.CommandViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val viewModel: CommandViewModel = hiltViewModel()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(modifier = Modifier.width(200.dp), onClick = {
                        viewModel.sendCommand(Command("rundll32.exe powrprof.dll SetSuspendState Sleep"))
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.PowerSettingsNew,
                            contentDescription = null
                        )
                        Text(modifier = Modifier.padding(start = 8.dp), text = "Hibernate")
                    }

                    ElevatedButton(modifier = Modifier.width(200.dp), onClick = { shareData() }) {
                        Text(text = "Send Text")
                    }
                }

                if ("text/plain" == intent.type) {
                    handleSendText(viewModel, intent)
                }
            }
        }
    }

    private fun handleSendText(viewModel: CommandViewModel, intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
            if (incomingText.matches(Regex(URL_REGEX))) {
                viewModel.sendCommand(Command("cmd /c start chrome $incomingText"))
                finishAffinity()
            } else {
                println(incomingText)
            }
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
