package com.shrihari.smartcampusnavigator.data.localization

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationRepository @Inject constructor() {

    // Live predicted node
    private val _currentNode = MutableStateFlow("N13")
    val currentNode: StateFlow<String> = _currentNode.asStateFlow()

    // Last selected destination
    private val _recentDestination = MutableStateFlow("No recent destination")
    val recentDestination: StateFlow<String> = _recentDestination.asStateFlow()

    fun updateCurrentNode(node: String) {
        _currentNode.value = node
    }

    fun updateRecentDestination(destination: String) {
        _recentDestination.value = destination
    }
}