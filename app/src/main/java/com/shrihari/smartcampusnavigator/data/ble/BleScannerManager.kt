package com.shrihari.smartcampusnavigator.data.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import com.shrihari.smartcampusnavigator.data.model.BleDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScannerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private companion object {
        const val BEACON_PREFIX = "TRACER_"
    }

    // -------------------------------------------------------------------------
    // Bluetooth Objects
    // -------------------------------------------------------------------------

    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    private val bluetoothAdapter: BluetoothAdapter?
        get() = bluetoothManager.adapter

    private val bluetoothLeScanner: BluetoothLeScanner?
        get() = bluetoothAdapter?.bluetoothLeScanner


    // -------------------------------------------------------------------------
    // Scanner Configuration
    // -------------------------------------------------------------------------

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()


    // -------------------------------------------------------------------------
    // Device Storage
    // -------------------------------------------------------------------------

    private val deviceCache = DeviceCache()


    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private val _devices = MutableStateFlow(deviceCache.getDevices())
    val devices: StateFlow<List<BleDevice>> =
        _devices.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> =
        _isScanning.asStateFlow()


    // -------------------------------------------------------------------------
    // Scanner Callback
    // -------------------------------------------------------------------------

    private val scanCallback = object : ScanCallback() {

        @SuppressLint("MissingPermission")
        override fun onScanResult(
            callbackType: Int,
            result: ScanResult
        ) {

            val deviceName = result.device.name
                ?.removePrefix("=")

            // Ignore devices that are not Tracer beacons
            if (deviceName.isNullOrBlank() || !deviceName.startsWith(BEACON_PREFIX)) {
                return
            }

            val bleDevice = BleDevice(
                name = deviceName,
                address = result.device.address,
                rssi = result.rssi,
                lastSeen = System.currentTimeMillis()
            )

            deviceCache.update(bleDevice)

            _devices.value = deviceCache.getDevices()
        }
    }


    // -------------------------------------------------------------------------
    // Helper Functions
    // -------------------------------------------------------------------------

    @SuppressLint("MissingPermission")
    private fun isScannerReady(): Boolean {
        return bluetoothAdapter?.isEnabled == true &&
                bluetoothLeScanner != null
    }


    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    @SuppressLint("MissingPermission")
    fun startScan() {

        if (!isScannerReady() || _isScanning.value) return

        bluetoothLeScanner?.startScan(
            null,
            scanSettings,
            scanCallback
        )

        _isScanning.value = true
    }

    @SuppressLint("MissingPermission")
    fun stopScan() {

        if (!_isScanning.value) return

        bluetoothLeScanner?.stopScan(scanCallback)

        _isScanning.value = false
    }
}