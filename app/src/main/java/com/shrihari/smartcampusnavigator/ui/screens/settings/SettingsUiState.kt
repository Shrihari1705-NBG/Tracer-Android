package com.shrihari.smartcampusnavigator.ui.screens.settings

data class SettingsUiState(

    val darkThemeEnabled: Boolean = false,

    val bluetoothEnabled: Boolean = false,

    val locationEnabled: Boolean = false,

    val version: String = "1.0.0"

)