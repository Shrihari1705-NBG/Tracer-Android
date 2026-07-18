package com.shrihari.smartcampusnavigator.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.PrimaryButton
import com.shrihari.smartcampusnavigator.ui.components.StatusCard
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar
import com.shrihari.smartcampusnavigator.ui.components.WelcomeBanner
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme
import com.shrihari.smartcampusnavigator.ui.viewmodel.HomeViewModel
import androidx.compose.ui.res.painterResource
import com.shrihari.smartcampusnavigator.R

private val SuccessGreen = Color(0xFF4CAF50)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    HomeScreenContent(
        uiState = uiState,
        onStartScan = viewModel::onStartScan,
        onBottomNavSelected = viewModel::onBottomNavSelected
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    onStartScan: () -> Unit,
    onBottomNavSelected: (BottomNavItem) -> Unit
) {
    Scaffold(
        bottomBar = {
            android.util.Log.d(
                "Tracer",
                "HomeScreen selectedBottomNav = ${uiState.selectedBottomNav}"
            )
            BottomNavBar(
                selectedItem = uiState.selectedBottomNav,
                onItemSelected = onBottomNavSelected
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

            WelcomeBanner(
                title = "Welcome",
                message = "Ready to navigate your campus?"
            )

            StatusCard(
                title = "Bluetooth",
                status = uiState.bluetoothStatus,
                statusColor = MaterialTheme.colorScheme.error
            )

            StatusCard(
                title = "BLE Scanner",
                status = uiState.scannerStatus,
                statusColor = SuccessGreen
            )

            StatusCard(
                title = "Current Location",
                status = uiState.currentLocation,
                statusColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

            PrimaryButton(
                text = "Start Scan",
                onClick = onStartScan
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    TracerTheme {
        HomeScreenContent(
            uiState = HomeUiState(),
            onStartScan = {},
            onBottomNavSelected = {}
        )
    }
}