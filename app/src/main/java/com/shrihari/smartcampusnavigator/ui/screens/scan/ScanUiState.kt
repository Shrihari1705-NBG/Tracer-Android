package com.shrihari.smartcampusnavigator.ui.screens.scan

import com.shrihari.smartcampusnavigator.data.model.BleDevice

data class ScanUiState(

    // -------------------------------------------------------
    // Bluetooth
    // -------------------------------------------------------

    val bluetoothEnabled: Boolean = false,

    val showBluetoothDialog: Boolean = false,

    val permissionGranted: Boolean = true,

    // -------------------------------------------------------
    // Scanner
    // -------------------------------------------------------

    val isScanning: Boolean = false,

    val nearbyDevices: List<BleDevice> = emptyList(),

    // -------------------------------------------------------
    // Selected Localization Node
    // -------------------------------------------------------

    val selectedNode: String = "N1",

    // -------------------------------------------------------
    // Available Localization Nodes
    // Final Map = 28 Nodes
    // -------------------------------------------------------

    val availableNodes: List<String> = listOf(
        "N1",
        "N2",
        "N3",
        "N4",
        "N5",
        "N6",
        "N7",
        "N8",
        "N9",
        "N10",
        "N11",
        "N12",
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
        "N27",
        "N28"
    ),

    // -------------------------------------------------------
    // Dataset Collection Session
    // -------------------------------------------------------

    // Current collection session
    val currentSession: Int = 1,

    // Five independent collection sessions
    val availableSessions: List<Int> = listOf(
        1,
        2,
        3,
        4,
        5
    ),

    // -------------------------------------------------------
    // Fingerprint Collection
    // -------------------------------------------------------

    // Number of samples collected during current scan
    val sampleCount: Int = 0,

    // Recording time in seconds
    val elapsedTime: Long = 0L,

    // -------------------------------------------------------
    // Error
    // -------------------------------------------------------

    val error: String? = null
)