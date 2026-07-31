package com.shrihari.smartcampusnavigator.data.ble

import com.shrihari.smartcampusnavigator.data.model.BleDevice

class DeviceCache {

    /**
     * Stores BLE devices using MAC Address as the unique key.
     */
    private val devices = mutableMapOf<String, BleDevice>()

    /**
     * Adds a new device or updates an existing one.
     */
    fun update(device: BleDevice) {
        devices[device.address] = device
    }

    /**
     * Returns all devices sorted by RSSI.
     * Strongest signal appears first.
     */
    fun getDevices(): List<BleDevice> {
        return devices.values.sortedByDescending { it.rssi }
    }

    /**
     * Clears all cached devices.
     */
    fun clear() {
        devices.clear()
    }

    /**
     * Removes devices that haven't been seen
     * within the specified timeout.
     *
     * @param timeoutMillis Time in milliseconds.
     */
    fun removeExpiredDevices(timeoutMillis: Long) {
        val currentTime = System.currentTimeMillis()

        devices.entries.removeIf { entry ->
            currentTime - entry.value.lastSeen > timeoutMillis
        }
    }

    /**
     * Returns the current number of cached devices.
     */
    fun size(): Int {
        return devices.size
    }

    /**
     * Returns true if no devices are cached.
     */
    fun isEmpty(): Boolean {
        return devices.isEmpty()
    }
}