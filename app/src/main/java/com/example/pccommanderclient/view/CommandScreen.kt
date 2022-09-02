package com.example.pccommanderclient.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pccommanderclient.viewmodel.CommandViewModel

@Composable
fun CommandScreen(
    viewModel: CommandViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Button(modifier = Modifier.width(200.dp), onClick = {
            viewModel.sendCommand("\"cmd\", \"/c\", \"start chrome \" + \"https://www.youtube.com/feed/subscriptions\"")
        }) {
            Text(text = "Start Youtube")
        }

        Button(modifier = Modifier.width(200.dp), onClick = {
            viewModel.sendCommand("rundll32.exe powrprof.dll, SetSuspendState Sleep")
        }) {
            Text(text = "Hibernate")
        }
    }
}