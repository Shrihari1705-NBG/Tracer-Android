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

                // -------------------------------------------------
                // Locked navigation: never recalculate A*
                // Only advance along the locked route
                // -------------------------------------------------

                if (_uiState.value.isNavigationActive) {

                    val currentRoute = _uiState.value.route

                    if (currentRoute.isEmpty()) return@collect

                    val currentRouteNode = currentRoute.first().id

                    // Already at the current route node
                    if (node == currentRouteNode) {
                        return@collect
                    }

                    // Check if the next expected node is reached
                    if (currentRoute.size > 1) {

                        val nextNode = currentRoute[1].id

                        if (node == nextNode) {

                            val remainingRoute = currentRoute.drop(1)

                            _uiState.update {
                                it.copy(route = remainingRoute)
                            }

                            // Destination reached
                            if (remainingRoute.size == 1) {

                                _uiState.update {
                                    it.copy(
                                        route = emptyList(),
                                        lockedRoute = emptyList(),
                                        isNavigationActive = false
                                    )
                                }
                            }
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
                lockedRoute = path,
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
                lockedRoute = emptyList(),
                isNavigationActive = false
            )
        }
    }

}
