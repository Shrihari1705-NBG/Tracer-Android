package com.shrihari.smartcampusnavigator.ui.screens.scan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ScanUiState())

    val uiState = _uiState.asStateFlow()

}