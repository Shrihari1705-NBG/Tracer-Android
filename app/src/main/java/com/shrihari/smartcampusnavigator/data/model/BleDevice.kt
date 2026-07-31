package com.shrihari.smartcampusnavigator.data.model

data class BleDevice(
    val name: String?,
    val address: String,
    val rssi: Int,
    val lastSeen: Long
)