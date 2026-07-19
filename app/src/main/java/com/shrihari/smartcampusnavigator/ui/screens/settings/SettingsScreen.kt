package com.shrihari.smartcampusnavigator.ui.screens.settings

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar
import com.shrihari.smartcampusnavigator.ui.navigation.Screen
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.ApplicationCard
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.DevelopersCard
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.GuideCard
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.SettingsToggleCard
import com.shrihari.smartcampusnavigator.ui.theme.ThemeViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val activity = context as Activity

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val bluetoothPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            val granted = permissions.values.all { it }

            if (granted) {
                viewModel.requestEnableBluetooth(activity)
            }

        }

    val locationPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->

            if (granted) {
                viewModel.openLocationSettings(activity)
            }

        }

    SettingsScreenContent(
        navController = navController,
        themeViewModel = themeViewModel,
        uiState = uiState,

        onDarkThemeChanged = viewModel::onDarkThemeChanged,

        onBluetoothChanged = { enabled ->

            if (enabled) {

                if (viewModel.hasBluetoothPermission()) {

                    viewModel.requestEnableBluetooth(activity)

                } else {

                    bluetoothPermissionLauncher.launch(

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                            arrayOf(
                                Manifest.permission.BLUETOOTH_SCAN,
                                Manifest.permission.BLUETOOTH_CONNECT
                            )

                        } else {

                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )

                        }

                    )

                }

            } else {

                // Android does not allow apps to disable Bluetooth.
                // Open Bluetooth Settings instead.

                viewModel.openBluetoothSettings(activity)

            }

        },

        onLocationChanged = { enabled ->

            if (enabled) {

                if (viewModel.hasLocationPermission()) {

                    viewModel.openLocationSettings(activity)

                } else {

                    locationPermissionLauncher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )

                }

            } else {

                // Android does not allow apps to disable
                // Location Services directly.

                viewModel.openLocationSettings(activity)

            }

        }

    )

}

@Composable
private fun SettingsScreenContent(
    navController: NavController,
    themeViewModel: ThemeViewModel,
    uiState: SettingsUiState,
    onDarkThemeChanged: (Boolean) -> Unit,
    onBluetoothChanged: (Boolean) -> Unit,
    onLocationChanged: (Boolean) -> Unit
) {

    Scaffold(

        bottomBar = {

            BottomNavBar(

                selectedItem = BottomNavItem.Settings,

                onItemSelected = { item ->

                    when (item) {

                        BottomNavItem.Home ->
                            navController.navigate(Screen.Home.route)

                        BottomNavItem.Scan ->
                            navController.navigate(Screen.Scan.route)

                        BottomNavItem.Navigate ->
                            navController.navigate(Screen.Navigate.route)

                        BottomNavItem.Settings ->
                            navController.navigate(Screen.Settings.route)

                    }

                }

            )

        }

    ) { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            TracerTopBar(
                title = "Tracer",
                subtitle = "Indoor Navigation for Smart Campuses",
                logo = painterResource(id = R.drawable.tracer_logo)
            )

            SettingsToggleCard(
                darkThemeEnabled = uiState.darkThemeEnabled,
                bluetoothEnabled = uiState.bluetoothEnabled,
                locationEnabled = uiState.locationEnabled,

                onDarkThemeChanged = {

                    onDarkThemeChanged(it)
                    themeViewModel.setDarkTheme(it)

                },

                onBluetoothChanged = onBluetoothChanged,

                onLocationChanged = onLocationChanged

            )

            ApplicationCard(
                version = uiState.version
            )

            DevelopersCard()

            GuideCard()

        }

    }

}