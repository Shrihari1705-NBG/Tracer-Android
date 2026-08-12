package com.shrihari.smartcampusnavigator.ui.screens.scan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.ui.components.BeaconListCard
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.NodeSelectionCard
import com.shrihari.smartcampusnavigator.ui.components.PrimaryButton
import com.shrihari.smartcampusnavigator.ui.components.ScanStatusCard
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar
import com.shrihari.smartcampusnavigator.ui.navigation.Screen
import com.shrihari.smartcampusnavigator.ui.components.SampleCounterCard
import com.shrihari.smartcampusnavigator.ui.components.TimerCard
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth


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
    if (uiState.showBluetoothDialog) {

        AlertDialog(

            onDismissRequest = {
                viewModel.dismissBluetoothDialog()
            },

            title = {
                Text("Bluetooth Required")
            },

            text = {
                Text("Please enable Bluetooth before starting a scan.")
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        viewModel.dismissBluetoothDialog()
                    }
                ) {

                    Text("OK")

                }

            }

        )

    }


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

            // ---------------------------------------------------------
            // Node Selection
            // ---------------------------------------------------------

            NodeSelectionCard(
                selectedNode = uiState.selectedNode,
                nodes = uiState.availableNodes,
                onNodeSelected = viewModel::onNodeSelected
            )

            // ---------------------------------------------------------
            // Scan Status
            // ---------------------------------------------------------

            ScanStatusCard(
                bluetoothEnabled = uiState.bluetoothEnabled
            )

            TimerCard(
                elapsedTime = uiState.elapsedTime
            )

            SampleCounterCard(
                sampleCount = uiState.sampleCount
            )

            BeaconListCard(
                devices = uiState.nearbyDevices
            )

            // ---------------------------------------------------------
            // Start / Stop Scan
            // ---------------------------------------------------------

            PrimaryButton(
                text = if (uiState.isScanning)
                    "Stop Scan"
                else
                    "Start Scan",

                onClick = {
                    viewModel.toggleScan()
                }
            )

            PrimaryButton(
                text = "Export CSV",
                enabled = uiState.sampleCount > 0,
                onClick = {
                    viewModel.exportCsv()
                }
            )


            // ---------------------------------------------------------
            // QR Handoff Scanner
            // ---------------------------------------------------------

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text(
                        text = "Continue from Tracer Kiosk",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Scan the QR code displayed on the Tracer Kiosk to continue navigation on your phone.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    PrimaryButton(
                        text = "Open QR Scanner",
                        onClick = {
                            navController.navigate(Screen.QrScanner.route)
                        }
                    )
                }
            }

        }

    }

}