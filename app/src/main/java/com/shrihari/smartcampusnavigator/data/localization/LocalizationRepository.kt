package com.shrihari.smartcampusnavigator.data.localization

import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationRepository @Inject constructor() {

    // Stable current node used throughout the app
    private val _currentNode = MutableStateFlow("N13")
    val currentNode: StateFlow<String> = _currentNode.asStateFlow()

    // Last selected destination
    private val _recentDestination = MutableStateFlow<Destination?>(null)
    val recentDestination: StateFlow<Destination?> = _recentDestination.asStateFlow()

    // ---------------------------------------------------------
    // Stabilization state
    // ---------------------------------------------------------

    private var lastCandidateNode: String = "N13"
    private var consecutiveCount: Int = 0

    private val requiredConsecutivePredictions = 3

    // ---------------------------------------------------------
    // Update Current Node with Stabilization
    // ---------------------------------------------------------

    fun updateCurrentNode(predictedNode: String) {

        if (predictedNode == lastCandidateNode) {
            consecutiveCount++
        } else {
            lastCandidateNode = predictedNode
            consecutiveCount = 1
        }

        if (
            consecutiveCount >= requiredConsecutivePredictions &&
            _currentNode.value != predictedNode
        ) {
            _currentNode.value = predictedNode
        }
    }

    // ---------------------------------------------------------
    // Recent Destination
    // ---------------------------------------------------------

    fun updateRecentDestination(destination: Destination) {
        _recentDestination.value = destination
    }
}