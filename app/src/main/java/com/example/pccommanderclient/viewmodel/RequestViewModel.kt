package com.example.pccommanderclient.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pccommanderclient.model.Request
import com.example.pccommanderclient.repository.RequestRepository
import com.example.pccommanderclient.util.SentResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(private var requestRepository: RequestRepository) :
    ViewModel() {
    fun sendCommand(request: Request) {

        val result = mutableStateOf("")

        viewModelScope.launch(Dispatchers.IO) {
            when (requestRepository.sendCommand(request)) {
                is SentResult.Success -> {
                    result.value = "Success"
                }

                is SentResult.Error -> {
                    result.value = "Error"
                }
            }
        }
    }

    fun sendText(request: Request) {

        val result = mutableStateOf("")

        viewModelScope.launch(Dispatchers.IO) {
            when (requestRepository.sendText(request)) {
                is SentResult.Success -> {
                    result.value = "Success"
                }

                is SentResult.Error -> {
                    result.value = "Error"
                }
            }
        }
    }

    fun sendMediaKeys(request: Request) {
        val result = mutableStateOf("")

        viewModelScope.launch(Dispatchers.IO) {
            when (requestRepository.sendMediaKeys(request)) {
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