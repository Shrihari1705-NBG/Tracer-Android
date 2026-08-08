package com.shrihari.smartcampusnavigator.data.navigation.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationRepository @Inject constructor() {

    private val _pendingDestination = MutableStateFlow<String?>(null)

    val pendingDestination: StateFlow<String?> =
        _pendingDestination.asStateFlow()

    fun navigateTo(destinationName: String) {
        _pendingDestination.value = destinationName
    }

    fun clearPendingDestination() {
        _pendingDestination.value = null
    }
}