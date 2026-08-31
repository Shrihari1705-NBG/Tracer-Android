package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.BluetoothSearching
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents an item in the Tracer bottom navigation bar.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {

    object Home : BottomNavItem(
        route = "home",
        label = "Home",
        icon = Icons.Rounded.Home
    )

    object Scan : BottomNavItem(
        route = "scan",
        label = "Scan",
        icon = Icons.AutoMirrored.Rounded.BluetoothSearching
    )

    object Navigate : BottomNavItem(
        route = "navigate",
        label = "Navigate",
        icon = Icons.Rounded.Explore
    )

    object Settings : BottomNavItem(
        route = "settings",
        label = "Settings",
        icon = Icons.Rounded.Settings
    )
}