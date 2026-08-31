package com.shrihari.smartcampusnavigator.data.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import com.shrihari.smartcampusnavigator.data.model.BleDevice
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScannerManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {

        // ---------------------------------------------------------
        // Tracer Beacon Configuration
        // ---------------------------------------------------------

        private const val BEACON_PREFIX = "TRACER_"

        // Final system contains 15 Tracer beacons
        private val VALID_BEACONS = (1..15).map {
            "TRACER_B$it"
        }.toSet()

        // ---------------------------------------------------------
        // Device Expiration
        // ---------------------------------------------------------

        private const val EXPIRATION_TIMEOUT = 15_000L

        private const val CLEANUP_INTERVAL = 1_000L
    }

    // -------------------------------------------------------------------------
    // Bluetooth Objects
    // -------------------------------------------------------------------------

    private val bluetoothManager =
        context.getSystemService(
            Context.BLUETOOTH_SERVICE
        ) as BluetoothManager

    private val bluetoothAdapter: BluetoothAdapter?
        get() = bluetoothManager.adapter

    private val bluetoothLeScanner: BluetoothLeScanner?
        get() = bluetoothAdapter?.bluetoothLeScanner

    // -------------------------------------------------------------------------
    // Scanner Configuration
    // -------------------------------------------------------------------------

    private val scanSettings =
        ScanSettings.Builder()
            .setScanMode(
                ScanSettings.SCAN_MODE_LOW_LATENCY
            )
            .build()

    // -------------------------------------------------------------------------
    // Device Storage
    // -------------------------------------------------------------------------

    private val deviceCache = DeviceCache()

    // -------------------------------------------------------------------------
    // State
    // -------------------------------------------------------------------------

    private val _devices =
        MutableStateFlow(
            deviceCache.getDevices()
        )

    val devices: StateFlow<List<BleDevice>> =
        _devices.asStateFlow()

    private val _isScanning =
        MutableStateFlow(false)

    val isScanning: StateFlow<Boolean> =
        _isScanning.asStateFlow()

    // -------------------------------------------------------------------------
    // Cleanup Job
    // -------------------------------------------------------------------------

    private val scannerScope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.Default
        )

    private var cleanupJob: Job? = null

    // -------------------------------------------------------------------------
    // Scanner Callback
    // -------------------------------------------------------------------------

    private val scanCallback =
        object : ScanCallback() {

            @SuppressLint("MissingPermission")
            override fun onScanResult(
                callbackType: Int,
                result: ScanResult
            ) {

                val deviceName =
                    result.scanRecord
                        ?.deviceName
                        ?.removePrefix("=")

                Log.d(
                    "BLE_CALLBACK",
                    "ScanResult received: " +
                            "${result.scanRecord?.deviceName} " +
                            "RSSI=${result.rssi}"
                )

                Log.d(
                    "TRACER_SCAN",
                    "Name=$deviceName " +
                            "MAC=${result.device.address} " +
                            "RSSI=${result.rssi}"
                )

                // ---------------------------------------------------------
                // Ignore unnamed devices
                // ---------------------------------------------------------

                if (deviceName.isNullOrBlank()) {
                    return
                }

                // ---------------------------------------------------------
                // Ignore non-Tracer devices
                // ---------------------------------------------------------

                if (!deviceName.startsWith(BEACON_PREFIX)) {
                    return
                }

                // ---------------------------------------------------------
                // Accept only B1 -> B15
                // ---------------------------------------------------------

                if (deviceName !in VALID_BEACONS) {

                    Log.d(
                        "TRACER_SCAN",
                        "Ignoring unknown Tracer device: $deviceName"
                    )

                    return
                }

                // ---------------------------------------------------------
                // Create BLE Device
                // ---------------------------------------------------------

                val bleDevice =
                    BleDevice(
                        name = deviceName,
                        address = result.device.address,
                        rssi = result.rssi,
                        lastSeen = System.currentTimeMillis()
                    )

                // ---------------------------------------------------------
                // Update Device Cache
                // ---------------------------------------------------------

                deviceCache.update(
                    bleDevice
                )

                _devices.value =
                    deviceCache.getDevices()

                Log.d(
                    "BLE_CALLBACK",
                    "Valid Tracer Beacon: $deviceName"
                )

                Log.d(
                    "BLE_CALLBACK",
                    "Devices Flow Updated = " +
                            "${_devices.value.size}"
                )
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
    // Start Cleanup Job
    // -------------------------------------------------------------------------

    private fun startCleanupJob() {

        cleanupJob?.cancel()

        cleanupJob =
            scannerScope.launch {

                while (
                    isActive &&
                    _isScanning.value
                ) {

                    delay(
                        CLEANUP_INTERVAL
                    )

                    deviceCache.removeExpiredDevices(
                        EXPIRATION_TIMEOUT
                    )

                    _devices.value =
                        deviceCache.getDevices()
                }
            }
    }

    // -------------------------------------------------------------------------
    // Stop Cleanup Job
    // -------------------------------------------------------------------------

    private fun stopCleanupJob() {

        cleanupJob?.cancel()

        cleanupJob = null
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    @SuppressLint("MissingPermission")
    fun startScan() {

        Log.d(
            "BLE_MANAGER",
            "startScan() called, " +
                    "isScanning=${_isScanning.value}"
        )

        // ---------------------------------------------------------
        // Check Scanner
        // ---------------------------------------------------------

        if (!isScannerReady()) {

            Log.d(
                "BLE_MANAGER",
                "Scanner NOT ready"
            )

            return
        }

        // ---------------------------------------------------------
        // Prevent Duplicate Scan
        // ---------------------------------------------------------

        if (_isScanning.value) {

            Log.d(
                "BLE_MANAGER",
                "Already scanning"
            )

            return
        }

        // ---------------------------------------------------------
        // Start BLE Scan
        // ---------------------------------------------------------

        bluetoothLeScanner?.startScan(
            null,
            scanSettings,
            scanCallback
        )

        Log.d(
            "BLE_MANAGER",
            "BLE Scan Started"
        )

        _isScanning.value = true

        startCleanupJob()
    }

    // -------------------------------------------------------------------------
    // Stop Scan
    // -------------------------------------------------------------------------

    @SuppressLint("MissingPermission")
    fun stopScan() {

        if (!_isScanning.value) {
            return
        }

        bluetoothLeScanner?.stopScan(
            scanCallback
        )

        _isScanning.value = false

        stopCleanupJob()

        // ---------------------------------------------------------
        // Clear Cached Devices
        // ---------------------------------------------------------

        deviceCache.clear()

        _devices.value = emptyList()

        Log.d(
            "BLE_MANAGER",
            "BLE Scan Stopped"
        )
    }
}