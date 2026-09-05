package com.shrihari.smartcampusnavigator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.shrihari.smartcampusnavigator.data.ble.BleScannerManager
import com.shrihari.smartcampusnavigator.data.datastore.SettingsDataStore
import com.shrihari.smartcampusnavigator.data.localization.LocalizationRepository
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
    private val localizationRepository: LocalizationRepository,
    private val navigationRepository: NavigationRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(HomeUiState())

    val uiState =
        _uiState.asStateFlow()


    // =========================================================================
    // INITIALIZATION
    // =========================================================================

    init {

        loadWelcomeMessage()

        observeBluetoothState()

        observeBeaconStatus()

        observeNavigationState()
    }


    // =========================================================================
    // WELCOME MESSAGE
    // =========================================================================

    private fun loadWelcomeMessage() {

        viewModelScope.launch {

            val message =
                repository.getWelcomeMessage()

            _uiState.update {

                it.copy(
                    welcomeMessage = message
                )
            }
        }
    }


    // =========================================================================
    // NAVIGATION STATE
    // =========================================================================

    private fun observeNavigationState() {

        // ---------------------------------------------------------------------
        // Live Current Location
        // ---------------------------------------------------------------------

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


        // ---------------------------------------------------------------------
        // Persisted Recent Destination
        // ---------------------------------------------------------------------

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


    // =========================================================================
    // NAVIGATE TO RECENT DESTINATION
    // =========================================================================

    fun navigateToRecentDestination() {

        val destination =
            _uiState.value.recentDestination

        if (destination != "No recent destination") {

            navigationRepository.navigateTo(
                destination
            )
        }
    }


    // =========================================================================
    // BLUETOOTH STATE
    // =========================================================================

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


    // =========================================================================
    // BLE BEACON STATUS
    // =========================================================================
    //
    // IMPORTANT:
    //
    // ONNX localization has intentionally been removed from this stage.
    //
    // We are currently collecting the new fingerprint dataset using:
    //
    // 15 Beacons
    // B1 ... B15
    //
    // 28 Nodes
    // N1 ... N28
    //
    // The old ONNX model expected 7 inputs and therefore caused a crash
    // when the new 15-dimensional RSSI vector was passed to it.
    //
    // The scanner itself remains active.
    // Only ML prediction has been disabled.
    //
    // =========================================================================

    private fun observeBeaconStatus() {

        viewModelScope.launch {

            bleScannerManager.devices.collect { devices ->

                // -----------------------------------------------------------------
                // Beacon Detection Status
                // -----------------------------------------------------------------

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


                // -----------------------------------------------------------------
                // Update Scanner Status
                // -----------------------------------------------------------------
                //
                // No ONNX prediction is performed here.
                //
                // -----------------------------------------------------------------

                _uiState.update {

                    it.copy(
                        scannerStatus = status
                    )
                }
            }
        }
    }


    // =========================================================================
    // ACTUAL NODE SELECTION
    // =========================================================================

    fun onActualNodeSelected(node: String) {

        _uiState.update {

            it.copy(
                selectedActualNode = node
            )
        }
    }


    // =========================================================================
    // START SCAN
    // =========================================================================

    fun onStartScan() {

        if (!_uiState.value.bluetoothEnabled) {

            return
        }

        bleScannerManager.startScan()
    }


    // =========================================================================
    // BOTTOM NAVIGATION
    // =========================================================================

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