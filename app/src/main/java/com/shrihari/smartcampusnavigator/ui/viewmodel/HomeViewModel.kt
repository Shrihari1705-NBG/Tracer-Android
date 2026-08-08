package com.shrihari.smartcampusnavigator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.ble.BleScannerManager
import com.shrihari.smartcampusnavigator.data.ml.OnnxLocalizationManager
import com.shrihari.smartcampusnavigator.domain.repository.HomeRepository
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.screens.home.HomeUiState
import com.shrihari.smartcampusnavigator.utils.BluetoothManager
import com.shrihari.smartcampusnavigator.data.localization.LocalizationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.shrihari.smartcampusnavigator.data.navigation.NodeNameMapper

@HiltViewModel
class HomeViewModel @Inject constructor(

    private val repository: HomeRepository,

    private val bluetoothManager: BluetoothManager,

    private val bleScannerManager: BleScannerManager,

    private val onnxLocalizationManager: OnnxLocalizationManager,

    private val localizationRepository: LocalizationRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {

        loadWelcomeMessage()

        observeBluetoothState()

        observeBeaconStatus()

        observeNavigationState()
    }

    // ---------------------------------------------------------
    // Welcome Message
    // ---------------------------------------------------------

    private fun loadWelcomeMessage() {

        viewModelScope.launch {

            val message = repository.getWelcomeMessage()

            _uiState.update {

                it.copy(
                    welcomeMessage = message
                )

            }

        }

    }

    // ---------------------------------------------------------
    // Observer Navigation State
    // ---------------------------------------------------------


    private fun observeNavigationState() {

        viewModelScope.launch {

            localizationRepository.currentNode.collect { node ->

                _uiState.update {
                    it.copy(
                        currentLocation = NodeNameMapper.getDisplayName(node)
                    )
                }
            }
        }

        viewModelScope.launch {

            localizationRepository.recentDestination.collect { destination ->

                _uiState.update {
                    it.copy(recentDestination = destination)
                }
            }
        }
    }

    // ---------------------------------------------------------
    // Bluetooth State
    // ---------------------------------------------------------

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

    // ---------------------------------------------------------
    // BLE + ML Localization
    // ---------------------------------------------------------

    private fun observeBeaconStatus() {

        viewModelScope.launch {

            bleScannerManager.devices.collect { devices ->

                val status = when {

                    !_uiState.value.bluetoothEnabled ->
                        "Bluetooth Off"

                    devices.isEmpty() ->
                        "Waiting for Tracer Beacons..."

                    devices.size == 1 ->
                        "1 Beacon Detected"

                    else ->
                        "${devices.size} Beacons Detected"

                }

                // ---------------------------------------------
                // Build RSSI Vector
                // Missing beacons = -100
                // ---------------------------------------------

                val rssiVector = FloatArray(7) { -100f }

                devices.forEach { device ->

                    when (device.name) {

                        "TRACER_B1" -> rssiVector[0] = device.rssi.toFloat()

                        "TRACER_B2" -> rssiVector[1] = device.rssi.toFloat()

                        "TRACER_B3" -> rssiVector[2] = device.rssi.toFloat()

                        "TRACER_B4" -> rssiVector[3] = device.rssi.toFloat()

                        "TRACER_B5" -> rssiVector[4] = device.rssi.toFloat()

                        "TRACER_B6" -> rssiVector[5] = device.rssi.toFloat()

                        "TRACER_B7" -> rssiVector[6] = device.rssi.toFloat()

                    }

                }

                Log.d(
                    "TRACER_ML",
                    "RSSI = ${rssiVector.joinToString()}"
                )

                // ---------------------------------------------------------
                // Wait until at least one Tracer beacon is detected
                // ---------------------------------------------------------

                if (devices.isEmpty()) {

                    _uiState.update {

                        it.copy(

                            scannerStatus = status,

                            predictedNode = "Waiting for Beacons..."

                        )

                    }

                    return@collect

                }

                // ---------------------------------------------
                // Predict Current Node
                // ---------------------------------------------

                val predictedNode =
                    onnxLocalizationManager.predictNode(rssiVector)

                localizationRepository.updateCurrentNode(predictedNode)

                Log.d(
                    "TRACER_ML",
                    "Prediction = $predictedNode"
                )

                // ---------------------------------------------
                // Update UI
                // ---------------------------------------------

                _uiState.update {

                    it.copy(

                        scannerStatus = status,

                        predictedNode = predictedNode

                    )

                }

            }

        }

    }

    fun onActualNodeSelected(node: String) {

        _uiState.update {

            it.copy(
                selectedActualNode = node
            )

        }

    }

    // ---------------------------------------------------------
    // Start Scan
    // ---------------------------------------------------------

    fun onStartScan() {

        Log.d(
            "HOME_SCAN",
            "Start Scan Button Pressed"
        )

        if (!_uiState.value.bluetoothEnabled) {

            Log.d(
                "HOME_SCAN",
                "Bluetooth OFF"
            )

            return

        }

        bleScannerManager.startScan()

    }


    // ---------------------------------------------------------
    // Bottom Navigation
    // ---------------------------------------------------------

    fun onBottomNavSelected(item: BottomNavItem) {

        _uiState.update {

            it.copy(
                selectedBottomNav = item
            )

        }

    }

}