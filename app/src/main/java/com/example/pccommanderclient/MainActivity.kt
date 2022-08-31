package com.example.pccommanderclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pccommanderclient.ui.theme.PCCommanderClientTheme

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CommandButton(
            text = "Start YouTube",
            command = "\"cmd\", \"/c\", \"start chrome \" + \"https://www.youtube.com/feed/subscriptions\""
        )
        CommandButton(
            text = "Hibernate",
            command = "rundll32.exe powrprof.dll, SetSuspendState Sleep"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PCCommanderClientTheme {
        MainScreen()
    }
}