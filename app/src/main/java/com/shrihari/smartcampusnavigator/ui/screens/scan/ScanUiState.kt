package com.shrihari.smartcampusnavigator.ui.screens.scan

import com.shrihari.smartcampusnavigator.data.model.BleDevice

data class ScanUiState(

    val bluetoothEnabled: Boolean = false,

    val showBluetoothDialog: Boolean = false,

    val permissionGranted: Boolean = true,

    val isScanning: Boolean = false,

    val nearbyDevices: List<BleDevice> = emptyList(),

    // Selected Navigation Node
    val selectedNode: String = "N13",

    // Available Nodes (Phase 1 - Left Wing)
    val availableNodes: List<String> = listOf(
        "N13",
        "N14",
        "N15",
        "N16",
        "N17",
        "N18",
        "N19",
        "N20",
        "N21",
        "N22",
        "N23",
        "N24",
        "N25",
        "N26",
        "N27"
    ),

    // Number of fingerprint samples collected
    val sampleCount: Int = 0,

    // Elapsed recording time (seconds)
    val elapsedTime: Long = 0L,

    val error: String? = null
)