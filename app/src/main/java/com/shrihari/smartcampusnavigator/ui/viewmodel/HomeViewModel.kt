package com.shrihari.smartcampusnavigator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.domain.repository.HomeRepository
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.screens.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadWelcomeMessage()
    }

    private fun loadWelcomeMessage() {
        viewModelScope.launch {
            val message = repository.getWelcomeMessage()

            _uiState.update {
                it.copy(
                    welcomeMessage = message
                )
            }
        }
    }

    fun onStartScan() {
        // TODO: Implement BLE scan
    }

    fun onBottomNavSelected(item: BottomNavItem) {
        _uiState.update {
            it.copy(
                selectedBottomNav = item
            )
        }
    }
}