package com.shrihari.smartcampusnavigator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _welcomeMessage = MutableStateFlow("Loading...")
    val welcomeMessage: StateFlow<String> = _welcomeMessage.asStateFlow()

    init {
        loadWelcomeMessage()
    }

    private fun loadWelcomeMessage() {
        viewModelScope.launch {
            val message = repository.getWelcomeMessage()
            _welcomeMessage.value = message
        }
    }
}
