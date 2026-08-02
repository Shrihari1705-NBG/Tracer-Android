package com.shrihari.smartcampusnavigator.ui.screens.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.ble.BleScannerManager
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
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shrihari.smartcampusnavigator.data.export.CsvExportManager
import android.util.Log
import android.widget.Toast

@HiltViewModel
class ScanViewModel @Inject constructor(
    application: Application,
    private val bluetoothManager: BluetoothManager,
    private val bleScannerManager: BleScannerManager
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState = _uiState.asStateFlow()

    // Stores all collected fingerprint samples
    private val fingerprintSamples = mutableListOf<FingerprintSample>()

    private val csvExportManager =
        CsvExportManager(getApplication())

    private var timerJob: Job? = null

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

        bleScannerManager.startScan()

        startTimer()
    }

    fun stopScan() {

        bleScannerManager.stopScan()

        timerJob?.cancel()
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

        viewModelScope.launch {

            bleScannerManager.devices.collect { devices ->

                _uiState.update {

                    it.copy(
                        nearbyDevices = devices.sortedBy { device ->
                            device.name
                        }
                    )

                }

                // Save one fingerprint sample whenever devices update
                collectFingerprint(devices)

            }

        }

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

        if (!_uiState.value.isScanning) return

        val rssiMap = devices
            .filter { !it.name.isNullOrBlank() }
            .associate { device ->
                device.name!! to device.rssi
            }

        fingerprintSamples.add(

            FingerprintSample(

                node = _uiState.value.selectedNode,

                timestamp = System.currentTimeMillis(),

                rssiValues = rssiMap

            )

        )

        Log.d("TRACER_DATA", "----------------------------")
        Log.d("TRACER_DATA", "Node = ${_uiState.value.selectedNode}")
        Log.d("TRACER_DATA", "Sample = ${fingerprintSamples.size}")

        rssiMap.forEach { (beacon, rssi) ->
            Log.d("TRACER_DATA", "$beacon : $rssi")
        }

        Log.d("TRACER_DATA", "----------------------------")

        _uiState.update {

            it.copy(
                sampleCount = fingerprintSamples.size
            )

        }

    }
    private fun startTimer() {

        timerJob?.cancel()

        timerJob = viewModelScope.launch {

            var seconds = 0L

            while (isActive && _uiState.value.isScanning) {

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

        fingerprintSamples.clear()

        _uiState.update {
            it.copy(
                sampleCount = 0,
                elapsedTime = 0L
            )
        }

        timerJob?.cancel()

        Log.d(
            "TRACER_EXPORT",
            "CSV Exported -> ${file.absolutePath}"
        )
    }

}