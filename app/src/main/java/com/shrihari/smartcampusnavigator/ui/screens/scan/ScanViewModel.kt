package com.shrihari.smartcampusnavigator.ui.screens.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.utils.BluetoothManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shrihari.smartcampusnavigator.data.ble.BleScannerManager

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val bluetoothManager: BluetoothManager,
    private val bleScannerManager: BleScannerManager
) : ViewModel() {

    fun startScan() {
        bleScannerManager.startScan()
    }

    fun stopScan() {
        bleScannerManager.stopScan()
    }

    fun toggleScan() {

        if (_uiState.value.isScanning) {
            stopScan()
        } else {
            startScan()
        }
    }

    private val _uiState = MutableStateFlow(ScanUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeBluetoothState()
        observeScannerState()
    }

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
    private fun observeScannerState() {

        viewModelScope.launch {

            bleScannerManager.devices.collect { devices ->

                _uiState.update {
                    it.copy(
                        nearbyDevices = devices
                    )
                }
            }
        }

        viewModelScope.launch {

            bleScannerManager.isScanning.collect { scanning ->

                _uiState.update {
                    it.copy(
                        isScanning = scanning
                    )
                }
            }
        }
    }

}