package com.shrihari.smartcampusnavigator.ui.screens.scan

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.ble.BleScannerManager
import com.shrihari.smartcampusnavigator.data.export.CsvExportManager
import com.shrihari.smartcampusnavigator.data.model.BleDevice
import com.shrihari.smartcampusnavigator.data.model.FingerprintSample
import com.shrihari.smartcampusnavigator.utils.BluetoothManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    application: Application,
    private val bluetoothManager: BluetoothManager,
    private val bleScannerManager: BleScannerManager
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState = _uiState.asStateFlow()

    // -------------------------------------------------------
    // Fingerprint Samples
    // -------------------------------------------------------

    // Stores all collected fingerprint samples
    private val fingerprintSamples =
        mutableListOf<FingerprintSample>()

    // -------------------------------------------------------
    // CSV Export
    // -------------------------------------------------------

    private val csvExportManager =
        CsvExportManager(getApplication())

    // -------------------------------------------------------
    // Timer
    // -------------------------------------------------------

    private var timerJob: Job? = null

    // -------------------------------------------------------
    // Session
    // -------------------------------------------------------

    /*
     * Current collection session.
     *
     * Session 1 = first collection session
     * Session 2 = second collection session
     * Session 3 = third collection session
     * Session 4 = fourth collection session
     * Session 5 = fifth collection session
     */
    private var currentSession = 2

    init {
        observeBluetoothState()
        observeScannerState()
    }

    // -------------------------------------------------------
    // Node Selection
    // -------------------------------------------------------

    fun onNodeSelected(node: String) {

        _uiState.update {
            it.copy(
                selectedNode = node
            )
        }
    }

    // -------------------------------------------------------
    // Session Selection
    // -------------------------------------------------------

    fun setSession(session: Int) {

        if (session !in 1..5) {
            Log.d(
                "TRACER_SESSION",
                "Invalid session: $session"
            )
            return
        }

        currentSession = session

        Log.d(
            "TRACER_SESSION",
            "Current Session = $currentSession"
        )
    }

    // -------------------------------------------------------
    // Scanner Controls
    // -------------------------------------------------------

    fun startScan() {

        _uiState.update {
            it.copy(
                sampleCount = 0,
                elapsedTime = 0L
            )
        }

        fingerprintSamples.clear()

        Log.d(
            "TRACER_SESSION",
            "Starting Session $currentSession"
        )

        Log.d(
            "TRACER_DATA",
            "Collecting Node = ${_uiState.value.selectedNode}"
        )

        bleScannerManager.startScan()

        startTimer()
    }

    fun stopScan() {

        bleScannerManager.stopScan()

        timerJob?.cancel()

        Log.d(
            "TRACER_SESSION",
            "Session $currentSession stopped"
        )
    }

    fun toggleScan() {

        if (_uiState.value.isScanning) {

            stopScan()

            return
        }

        if (!_uiState.value.bluetoothEnabled) {

            _uiState.update {
                it.copy(
                    showBluetoothDialog = true
                )
            }

            return
        }

        startScan()
    }

    fun dismissBluetoothDialog() {

        _uiState.update {
            it.copy(
                showBluetoothDialog = false
            )
        }
    }

    // -------------------------------------------------------
    // Bluetooth State
    // -------------------------------------------------------

    private fun observeBluetoothState() {

        viewModelScope.launch {

            bluetoothManager.bluetoothState.collect { enabled ->

                _uiState.update {

                    it.copy(
                        bluetoothEnabled = enabled
                    )
                }
            }
        }
    }

    // -------------------------------------------------------
    // BLE Scanner State
    // -------------------------------------------------------

    private fun observeScannerState() {

        // ---------------------------------------------------
        // Nearby Devices
        // ---------------------------------------------------

        viewModelScope.launch {

            bleScannerManager.devices.collect { devices ->

                _uiState.update {

                    it.copy(
                        nearbyDevices = devices.sortedBy { device ->
                            device.name
                        }
                    )
                }

                // Save one fingerprint sample whenever
                // the BLE device list is updated.
                collectFingerprint(devices)
            }
        }

        // ---------------------------------------------------
        // Scanner State
        // ---------------------------------------------------

        viewModelScope.launch {

            bleScannerManager.isScanning.collect { scanning ->

                _uiState.update {

                    it.copy(
                        isScanning = scanning
                    )
                }

                if (!scanning) {

                    timerJob?.cancel()
                }
            }
        }
    }

    // -------------------------------------------------------
    // Fingerprint Collection
    // -------------------------------------------------------

    private fun collectFingerprint(
        devices: List<BleDevice>
    ) {

        if (!_uiState.value.isScanning) {
            return
        }

        // ---------------------------------------------------
        // Build RSSI Map
        // ---------------------------------------------------

        val rssiMap = devices
            .filter { !it.name.isNullOrBlank() }
            .associate { device ->

                device.name!! to device.rssi
            }

        // ---------------------------------------------------
        // Create Fingerprint Sample
        // ---------------------------------------------------

        val sample = FingerprintSample(

            node = _uiState.value.selectedNode,

            timestamp = System.currentTimeMillis(),

            session = currentSession,

            rssiValues = rssiMap
        )

        fingerprintSamples.add(sample)

        // ---------------------------------------------------
        // Debug Logging
        // ---------------------------------------------------

        Log.d(
            "TRACER_DATA",
            "----------------------------"
        )

        Log.d(
            "TRACER_DATA",
            "Node = ${_uiState.value.selectedNode}"
        )

        Log.d(
            "TRACER_DATA",
            "Session = $currentSession"
        )

        Log.d(
            "TRACER_DATA",
            "Sample = ${fingerprintSamples.size}"
        )

        rssiMap.forEach { (beacon, rssi) ->

            Log.d(
                "TRACER_DATA",
                "$beacon : $rssi"
            )
        }

        Log.d(
            "TRACER_DATA",
            "----------------------------"
        )

        // ---------------------------------------------------
        // Update Sample Count
        // ---------------------------------------------------

        _uiState.update {

            it.copy(
                sampleCount = fingerprintSamples.size
            )
        }
    }

    // -------------------------------------------------------
    // Timer
    // -------------------------------------------------------

    private fun startTimer() {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            var seconds = 0L

            while (
                isActive &&
                _uiState.value.isScanning
            ) {

                delay(1000)

                seconds++

                _uiState.update {

                    it.copy(
                        elapsedTime = seconds
                    )
                }
            }
        }
    }

    // -------------------------------------------------------
    // CSV Export
    // -------------------------------------------------------

    fun exportCsv() {

        if (fingerprintSamples.isEmpty()) {

            Toast.makeText(
                getApplication(),
                "No samples available to export.",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val file = csvExportManager.exportSamples(

            node = _uiState.value.selectedNode,

            samples = fingerprintSamples
        )

        Toast.makeText(
            getApplication(),
            "CSV saved successfully!",
            Toast.LENGTH_LONG
        ).show()

        Log.d(
            "TRACER_EXPORT",
            "CSV Exported -> ${file.absolutePath}"
        )

        Log.d(
            "TRACER_EXPORT",
            "Node = ${_uiState.value.selectedNode}"
        )

        Log.d(
            "TRACER_EXPORT",
            "Session = $currentSession"
        )

        Log.d(
            "TRACER_EXPORT",
            "Samples = ${fingerprintSamples.size}"
        )

        // ---------------------------------------------------
        // Clear Current Collection
        // ---------------------------------------------------

        fingerprintSamples.clear()

        _uiState.update {

            it.copy(
                sampleCount = 0,
                elapsedTime = 0L
            )
        }

        timerJob?.cancel()
    }
}