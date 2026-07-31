package com.shrihari.smartcampusnavigator.ui.screens.scan

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shrihari.smartcampusnavigator.ui.navigation.Screen

import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.ui.components.BeaconListCard
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.PrimaryButton
import com.shrihari.smartcampusnavigator.ui.components.ScanStatusCard
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar

@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: ScanViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ScanScreenContent(
        navController = navController,
        uiState = uiState.value,
        viewModel = viewModel
    )
}


@Composable
private fun ScanScreenContent(
    navController: NavController,
    uiState: ScanUiState,
    viewModel: ScanViewModel
) {

    Scaffold(

        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavItem.Scan,
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

            ScanStatusCard(
                bluetoothEnabled = uiState.bluetoothEnabled
            )

            BeaconListCard(
                devices = uiState.nearbyDevices
            )

            PrimaryButton(
                text = if (uiState.isScanning) "Stop Scan" else "Start Scan",
                onClick = {
                    viewModel.toggleScan()
                }
            )
        }
    }
}