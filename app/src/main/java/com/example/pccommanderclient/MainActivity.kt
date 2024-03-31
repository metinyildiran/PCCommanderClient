package com.example.pccommanderclient

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.material.icons.rounded.ArrowOutward
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.PowerSettingsNew
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pccommanderclient.composables.CustomAlertDialog
import com.example.pccommanderclient.model.Request
import com.example.pccommanderclient.ui.theme.AppTheme
import com.example.pccommanderclient.util.Commands
import com.example.pccommanderclient.util.Constants
import com.example.pccommanderclient.util.Constants.URL_REGEX
import com.example.pccommanderclient.util.MediaKeys
import com.example.pccommanderclient.viewmodel.RequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: RequestViewModel by viewModels()

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                viewModel.sendMediaKeys(Request(MediaKeys.VOLUME_UP))
                true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                viewModel.sendMediaKeys(Request(MediaKeys.VOLUME_DOWN))
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
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
                        Text(modifier = Modifier.padding(start = 8.dp), text = stringResource(R.string.hibernate))
                    }

                    if (showHibernateDialog) {
                        CustomAlertDialog(
                            title = { Text(text = stringResource(id = R.string.hibernate)) },
                            text = { Text(text = stringResource(R.string.do_you_really_want_to_hibernate_the_pc)) },
                            onDismiss = { showHibernateDialog = false }) {
                            viewModel.sendCommand(Request(Commands.HIBERNATE))
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

                                if (clipboardData.matches(Regex(URL_REGEX))) {
                                    viewModel.sendCommand(Request(Commands.startChromeWithUrl(clipboardData)))
                                } else {
                                    viewModel.sendText(Request(clipboardData))
                                }
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.clipboard_is_empty),
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
                            text = stringResource(R.string.send_text)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        FilledIconButton(onClick = {
                            viewModel.sendMediaKeys(Request(MediaKeys.PREVIOUS))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.SkipPrevious,
                                contentDescription = null
                            )
                        }

                        FilledIconButton(modifier = Modifier
                            .size(70.dp), onClick = {
                            viewModel.sendMediaKeys(Request(MediaKeys.PLAY_STOP))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = null
                            )
                        }


                        FilledIconButton(onClick = {
                            viewModel.sendMediaKeys(Request(MediaKeys.NEXT))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.SkipNext,
                                contentDescription = null
                            )
                        }
                    }
                }

                if (intent.type == Constants.INTENT_TYPE_TEXT_PLAIN) {
                    handleSendText(viewModel, intent)
                }
            }
        }
    }

    private fun handleSendText(viewModel: RequestViewModel, intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let { incomingText ->
            if (incomingText.matches(Regex(URL_REGEX))) {
                viewModel.sendCommand(Request(Commands.startChromeWithUrl(incomingText)))
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
            putExtra(Intent.EXTRA_TEXT, getString(R.string.this_is_my_text_to_send))
            type = Constants.INTENT_TYPE_TEXT_PLAIN
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
