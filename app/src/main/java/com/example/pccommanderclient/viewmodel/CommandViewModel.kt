package com.example.pccommanderclient.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pccommanderclient.model.Command
import com.example.pccommanderclient.repository.CommandRepository
import com.example.pccommanderclient.util.SentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommandViewModel @Inject constructor(var commandRepository: CommandRepository) : ViewModel() {
    fun sendCommand(command: Command) {

        val result = mutableStateOf("")

        viewModelScope.launch(Dispatchers.IO) {
            when (commandRepository.sendCommand(command)) {
                is SentResult.Success -> {
                    result.value = "Success"
                }
                is SentResult.Error -> {
                    result.value = "Error"
                }
            }
        }
    }
}