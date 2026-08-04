package com.shrihari.smartcampusnavigator.ui.screens.navigate

import androidx.lifecycle.ViewModel
import com.shrihari.smartcampusnavigator.data.navigation.algorithm.PathFinder
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NavigateViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NavigateUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * Temporary.
     * Later this will come directly from the ONNX Localization module.
     */
    fun updateCurrentNode(node: String) {

        _uiState.update {

            it.copy(
                currentNode = node
            )

        }

    }

    /**
     * User selects a destination.
     */
    fun selectDestination(destination: Destination) {

        _uiState.update {

            it.copy(
                selectedDestination = destination
            )

        }

    }

    /**
     * Runs A* and generates the shortest path.
     */
    fun startNavigation() {

        val state = _uiState.value

        val destination = state.selectedDestination ?: return

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