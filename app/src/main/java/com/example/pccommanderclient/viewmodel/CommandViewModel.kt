package com.example.pccommanderclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pccommanderclient.dependencyinjection.CommandSender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommandViewModel @Inject constructor(var commandSender: CommandSender) : ViewModel() {
    fun sendCommand(command: String) {
        viewModelScope.launch(Dispatchers.IO) {
            commandSender.sendCommand(command)
        }
    }
}