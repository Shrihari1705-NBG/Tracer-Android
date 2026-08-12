package com.shrihari.smartcampusnavigator.ui.screens.navigate

import com.shrihari.smartcampusnavigator.data.navigation.graph.GraphNode
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.Destination

data class NavigateUiState(

    // Current node predicted by ML
    val currentNode: String = "N13",

    // Destination selected from UI
    val selectedDestination: Destination? = null,

    // Remaining route shown on the map
    val route: List<GraphNode> = emptyList(),

    // Original route that never changes
    val lockedRoute: List<GraphNode> = emptyList(),

    // Navigation state
    val isNavigationActive: Boolean = false
)