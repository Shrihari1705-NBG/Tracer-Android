package com.shrihari.smartcampusnavigator.ui.screens.navigate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.datastore.SettingsDataStore
import com.shrihari.smartcampusnavigator.data.localization.LocalizationRepository
import com.shrihari.smartcampusnavigator.data.navigation.algorithm.PathFinder
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.DestinationData

@HiltViewModel
class NavigateViewModel @Inject constructor(
    private val localizationRepository: LocalizationRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {


    private val _uiState = MutableStateFlow(NavigateUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeCurrentLocation()
    }

    /**
     * Continuously observe the current node predicted by the
     * BLE + ONNX localization system.
     */
    private fun observeCurrentLocation() {

        viewModelScope.launch {

            localizationRepository.currentNode.collect { node ->

                _uiState.update {
                    it.copy(currentNode = node)
                }

                // If navigation is active, automatically recalculate the route
                if (_uiState.value.isNavigationActive) {

                    val destination = _uiState.value.selectedDestination

                    if (destination != null) {

                        // Destination reached
                        if (node == destination.nodeId) {

                            _uiState.update {
                                it.copy(
                                    route = emptyList(),
                                    isNavigationActive = false
                                )
                            }

                        } else {

                            recalculateRoute()

                        }
                    }
                }
            }
        }
    }

    fun startRecentNavigation() {

        viewModelScope.launch {

            settingsDataStore.recentDestination.collect { destinationName ->

                if (destinationName != "No recent destination") {

                    val destination = DestinationData.destinations.find {
                        it.name == destinationName
                    }

                    if (destination != null) {

                        _uiState.update {
                            it.copy(selectedDestination = destination)
                        }

                        startNavigation()
                    }

                    return@collect
                }
            }
        }
    }

    /**
     * User selects a destination.
     */
    fun selectDestination(destination: Destination) {

        _uiState.update {
            it.copy(selectedDestination = destination)
        }
    }

    /**
     * Start navigation.
     */
    fun startNavigation() {

        val state = _uiState.value

        val destination = state.selectedDestination ?: return

        // Save recent destination immediately
        viewModelScope.launch {
            settingsDataStore.setRecentDestination(destination.name)
        }

        val path = PathFinder.findPath(
            startNodeId = state.currentNode,
            destinationNodeId = destination.nodeId
        )

        _uiState.update {
            it.copy(
                route = path,
                isNavigationActive = true
            )
        }
    }

    /**
     * Automatically update the route whenever the
     * current node changes.
     */
    private fun recalculateRoute() {

        val state = _uiState.value

        val destination = state.selectedDestination ?: return

        val path = PathFinder.findPath(
            startNodeId = state.currentNode,
            destinationNodeId = destination.nodeId
        )

        _uiState.update {
            it.copy(route = path)
        }
    }

    /**
     * Stop navigation.
     */
    fun stopNavigation() {

        _uiState.update {
            it.copy(
                route = emptyList(),
                isNavigationActive = false
            )
        }
    }

}
