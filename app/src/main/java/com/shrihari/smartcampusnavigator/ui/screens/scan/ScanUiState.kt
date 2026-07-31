package com.shrihari.smartcampusnavigator.ui.screens.scan

import com.shrihari.smartcampusnavigator.data.model.BleDevice


data class ScanUiState(
    val bluetoothEnabled: Boolean = false,
    val permissionGranted: Boolean = true,
    val isScanning: Boolean = false,
    val nearbyDevices: List<BleDevice> = emptyList(),
    val error: String? = null
)
