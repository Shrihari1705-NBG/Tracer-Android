package com.shrihari.smartcampusnavigator.ui.screens.home

import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem

data class HomeUiState(
    val welcomeMessage: String = "Loading...",
    val bluetoothEnabled: Boolean = false,
    val scannerStatus: String = "Ready",
    val currentLocation: String = "Not Available",
    val selectedBottomNav: BottomNavItem = BottomNavItem.Home
)