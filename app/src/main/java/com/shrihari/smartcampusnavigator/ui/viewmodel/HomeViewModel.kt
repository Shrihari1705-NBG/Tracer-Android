package com.shrihari.smartcampusnavigator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.ble.BleScannerManager
import com.shrihari.smartcampusnavigator.data.datastore.SettingsDataStore
import com.shrihari.smartcampusnavigator.data.localization.LocalizationRepository
import com.shrihari.smartcampusnavigator.data.ml.OnnxLocalizationManager
import com.shrihari.smartcampusnavigator.data.navigation.NodeNameMapper
import com.shrihari.smartcampusnavigator.data.navigation.repository.NavigationRepository
import com.shrihari.smartcampusnavigator.domain.repository.HomeRepository
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.screens.home.HomeUiState
import com.shrihari.smartcampusnavigator.utils.BluetoothManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val bluetoothManager: BluetoothManager,
    private val bleScannerManager: BleScannerManager,
    private val onnxLocalizationManager: OnnxLocalizationManager,
    private val localizationRepository: LocalizationRepository,
    private val navigationRepository: NavigationRepository,
    private val settingsDataStore: SettingsDataStore
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
    // Observe Navigation State
    // ---------------------------------------------------------

    private fun observeNavigationState() {

        // -----------------------------------------------------
        // Live Current Location
        // -----------------------------------------------------

        viewModelScope.launch {

            localizationRepository.currentNode.collect { node ->

                _uiState.update {

                    it.copy(
                        currentLocation =
                            NodeNameMapper.getDisplayName(node)
                    )
                }
            }
        }

        // -----------------------------------------------------
        // Persisted Recent Destination
        // -----------------------------------------------------

        viewModelScope.launch {

            settingsDataStore.recentDestination.collect { destination ->

                _uiState.update {

                    it.copy(
                        recentDestination = destination
                    )
                }
            }
        }
    }

    // ---------------------------------------------------------
    // Navigate to Recent Destination
    // ---------------------------------------------------------

    fun navigateToRecentDestination() {

        val destination =
            _uiState.value.recentDestination

        if (destination != "No recent destination") {

            navigationRepository.navigateTo(
                destination
            )
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

                // -------------------------------------------------
                // Beacon Detection Status
                // -------------------------------------------------

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

                // -------------------------------------------------
                // Build 15-Dimensional RSSI Vector
                //
                // B1  -> index 0
                // B2  -> index 1
                // ...
                // B15 -> index 14
                //
                // Missing beacon = -100
                // -------------------------------------------------

                val rssiVector =
                    FloatArray(15) { -100f }

                devices.forEach { device ->

                    when (device.name) {

                        "TRACER_B1" ->
                            rssiVector[0] =
                                device.rssi.toFloat()

                        "TRACER_B2" ->
                            rssiVector[1] =
                                device.rssi.toFloat()

                        "TRACER_B3" ->
                            rssiVector[2] =
                                device.rssi.toFloat()

                        "TRACER_B4" ->
                            rssiVector[3] =
                                device.rssi.toFloat()

                        "TRACER_B5" ->
                            rssiVector[4] =
                                device.rssi.toFloat()

                        "TRACER_B6" ->
                            rssiVector[5] =
                                device.rssi.toFloat()

                        "TRACER_B7" ->
                            rssiVector[6] =
                                device.rssi.toFloat()

                        "TRACER_B8" ->
                            rssiVector[7] =
                                device.rssi.toFloat()

                        "TRACER_B9" ->
                            rssiVector[8] =
                                device.rssi.toFloat()

                        "TRACER_B10" ->
                            rssiVector[9] =
                                device.rssi.toFloat()

                        "TRACER_B11" ->
                            rssiVector[10] =
                                device.rssi.toFloat()

                        "TRACER_B12" ->
                            rssiVector[11] =
                                device.rssi.toFloat()

                        "TRACER_B13" ->
                            rssiVector[12] =
                                device.rssi.toFloat()

                        "TRACER_B14" ->
                            rssiVector[13] =
                                device.rssi.toFloat()

                        "TRACER_B15" ->
                            rssiVector[14] =
                                device.rssi.toFloat()
                    }
                }

                // -------------------------------------------------
                // Debug RSSI Vector
                // -------------------------------------------------

                Log.d(
                    "TRACER_ML",
                    "RSSI(15) = ${rssiVector.joinToString()}"
                )

                // -------------------------------------------------
                // Wait Until At Least One Tracer Beacon
                // -------------------------------------------------

                if (devices.isEmpty()) {

                    _uiState.update {

                        it.copy(
                            scannerStatus = status,
                            predictedNode =
                                "Waiting for Beacons..."
                        )
                    }

                    return@collect
                }

                // -------------------------------------------------
                // Predict Current Node
                // -------------------------------------------------

                /*
                 * IMPORTANT:
                 *
                 * The ONNX model currently installed in the
                 * application must accept 15 input features.
                 *
                 * Until the final 15-beacon model is trained
                 * and exported, this call will not work with
                 * the old 7-feature model.
                 */

                val predictedNode =
                    onnxLocalizationManager.predictNode(
                        rssiVector
                    )

                // -------------------------------------------------
                // Update Localization Repository
                // -------------------------------------------------

                localizationRepository.updateCurrentNode(
                    predictedNode
                )

                Log.d(
                    "TRACER_ML",
                    "Prediction = $predictedNode"
                )

                // -------------------------------------------------
                // Update UI
                // -------------------------------------------------

                _uiState.update {

                    it.copy(
                        scannerStatus = status,
                        predictedNode = predictedNode
                    )
                }
            }
        }
    }

    // ---------------------------------------------------------
    // Actual Node Selection
    // ---------------------------------------------------------

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

    fun onBottomNavSelected(
        item: BottomNavItem
    ) {

        _uiState.update {

            it.copy(
                selectedBottomNav = item
            )
        }
    }
}