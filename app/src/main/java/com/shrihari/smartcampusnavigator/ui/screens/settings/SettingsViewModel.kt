package com.shrihari.smartcampusnavigator.ui.screens.settings

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shrihari.smartcampusnavigator.data.repository.SettingsRepository
import com.shrihari.smartcampusnavigator.utils.BluetoothManager
import com.shrihari.smartcampusnavigator.utils.PermissionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

    private val settingsRepository: SettingsRepository,
    private val permissionManager: PermissionManager,
    private val bluetoothManager: BluetoothManager

) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> =
        combine(
            settingsRepository.darkThemeEnabled,
            bluetoothManager.bluetoothState
        ) { darkTheme, bluetooth ->

            SettingsUiState(
                darkThemeEnabled = darkTheme,
                bluetoothEnabled = bluetooth
            )

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsUiState()
        )

    fun hasBluetoothPermission(): Boolean {
        return permissionManager.hasBluetoothPermissions()
    }

    fun isBluetoothEnabled(): Boolean {
        return bluetoothManager.isBluetoothEnabled()
    }

    fun requestEnableBluetooth(activity: Activity) {
        bluetoothManager.requestEnableBluetooth(activity)
    }

    fun openBluetoothSettings(activity: Activity) {
        bluetoothManager.openBluetoothSettings(activity)
    }

    fun onDarkThemeChanged(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkTheme(enabled)
        }
    }

}