package com.shrihari.smartcampusnavigator.ui.screens.navigate

data class NavigateUiState(

    val currentLocation: String = "HOD (ECE) Cabin",

    val destination: String = "Block 6 (Smart Classroom)",

    val mapImageRes: Int? = null,

    val isNavigationActive: Boolean = false
)