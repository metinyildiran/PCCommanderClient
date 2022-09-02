package com.example.pccommanderclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pccommanderclient.ui.theme.PCCommanderClientTheme
import com.example.pccommanderclient.view.CommandScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PCCommanderClientTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "command_screen") {
                    composable("command_screen") {
                        CommandScreen()
                    }
                }
            }
        }
    }
}
