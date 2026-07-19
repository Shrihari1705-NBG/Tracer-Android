package com.shrihari.smartcampusnavigator.ui.screens.scan

data class ScanUiState(
    val bluetoothEnabled: Boolean = true,
    val permissionGranted: Boolean = true,
    val nearbyBeacons: List<Beacon> = listOf(
        Beacon("Beacon 1", "-62 dBm"),
        Beacon("Beacon 2", "-70 dBm"),
        Beacon("Beacon 3", "-81 dBm")
    )
)

data class Beacon(
    val name: String,
    val rssi: String
)