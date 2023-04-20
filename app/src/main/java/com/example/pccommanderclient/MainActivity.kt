package com.example.pccommanderclient

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowOutward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pccommanderclient.composables.CustomAlertDialog
import com.example.pccommanderclient.model.Request
import com.example.pccommanderclient.ui.theme.AppTheme
import com.example.pccommanderclient.util.Constants.URL_REGEX
import com.example.pccommanderclient.viewmodel.RequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val viewModel: RequestViewModel = hiltViewModel()
                var showHibernateDialog by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(modifier = Modifier.width(200.dp), onClick = {
                        showHibernateDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.PowerSettingsNew,
                            contentDescription = null
                        )
                        Text(modifier = Modifier.padding(start = 8.dp), text = "Hibernate")
                    }  // Hibernate Button

                    if (showHibernateDialog) {
                        CustomAlertDialog(
                            title = { Text(text = "Hibernate") },
                            text = { Text(text = "Do you really want to hibernate the PC?") },
                            onDismiss = { showHibernateDialog = false }) {
                            viewModel.sendCommand(Request("rundll32.exe powrprof.dll SetSuspendState Sleep"))
                            showHibernateDialog = false
                        }
                    }

                    ElevatedButton(modifier = Modifier.width(200.dp), onClick = {
                        val clipboard =
                            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                        if (clipboard.hasPrimaryClip()) {
                            if ((clipboard.primaryClipDescription?.hasMimeType(MIMETYPE_TEXT_PLAIN))!!) {
                                val clipboardData =
                                    clipboard.primaryClip?.getItemAt(0)?.text.toString()
                                viewModel.sendText(Request(clipboardData))
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Clipboard is empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowOutward,
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Send Text"
                        )
                    }  // Send Text Button
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        FilledIconButton(onClick = {
                            viewModel.sendMediaKeys(Request("previous"))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.SkipPrevious,
                                contentDescription = null
                            )
                        }

                        FilledIconButton(modifier = Modifier
                            .size(70.dp), onClick = {
                            viewModel.sendMediaKeys(Request("play/stop"))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = null
                            )
                        }


                        FilledIconButton(onClick = {
                            viewModel.sendMediaKeys(Request("next"))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.SkipNext,
                                contentDescription = null
                            )
                        }
                    }
                }  // Play/Pause Button

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Column {
                        FilledIconButton(onClick = {
                            viewModel.sendMediaKeys(Request("volume_up"))
                        }) {
                            Icon(
                                modifier = Modifier.size(50.dp),
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Volume up"
                            )
                        }

                        FilledIconButton(onClick = { viewModel.sendMediaKeys(Request("volume_down")) }) {
                            Icon(
                                modifier = Modifier.size(50.dp),
                                imageVector = Icons.Rounded.Remove,
                                contentDescription = "Volume down"
                            )
                        }
                    }
                }  // Volume Buttons

                if ("text/plain" == intent.type) {
                    handleSendText(viewModel, intent)
                }
            }
        }
    }

    private fun handleSendText(viewModel: RequestViewModel, intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
            if (incomingText.matches(Regex(URL_REGEX))) {
                viewModel.sendCommand(Request("cmd /c start chrome $incomingText"))
                finishAffinity()
            } else {
                viewModel.sendText(Request(incomingText))
                finishAffinity()
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
