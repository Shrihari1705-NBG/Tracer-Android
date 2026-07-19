package com.shrihari.smartcampusnavigator.ui.screens.navigate

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigateViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NavigateUiState())

    val uiState = _uiState.asStateFlow()

}