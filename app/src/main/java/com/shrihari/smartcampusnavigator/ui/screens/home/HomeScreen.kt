package com.shrihari.smartcampusnavigator.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar
import com.shrihari.smartcampusnavigator.ui.components.WelcomeBanner
import com.shrihari.smartcampusnavigator.ui.navigation.Screen
import com.shrihari.smartcampusnavigator.ui.screens.home.components.CurrentLocationCard
import com.shrihari.smartcampusnavigator.ui.screens.home.components.LocalizationCard
import com.shrihari.smartcampusnavigator.ui.screens.home.components.RecentDestinationCard
import com.shrihari.smartcampusnavigator.ui.screens.home.components.SystemStatusCard
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme
import com.shrihari.smartcampusnavigator.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showRecentDialog by remember { mutableStateOf(false) }

    HomeScreenContent(
        uiState = uiState,

        onRecentDestinationClick = {
            if (uiState.recentDestination != "No recent destination") {
                showRecentDialog = true
            }
        },

        onBottomNavSelected = { item ->
            when (item) {
                BottomNavItem.Home -> {
                    navController.navigate(Screen.Home.route)
                }

                BottomNavItem.Scan -> {
                    navController.navigate(Screen.Scan.route)
                }

                BottomNavItem.Navigate -> {
                    viewModel.navigateToRecentDestination()
                    navController.navigate(Screen.Navigate.route)
                }

                BottomNavItem.Settings -> {
                    navController.navigate(Screen.Settings.route)
                }
            }
        },

        onStartScan = {
            viewModel.onStartScan()
        },

        onActualNodeSelected = {
            viewModel.onActualNodeSelected(it)
        }
    )

    if (showRecentDialog) {
        AlertDialog(
            onDismissRequest = {
                showRecentDialog = false
            },

            title = {
                Text("Navigate again?")
            },

            text = {
                Text("Do you want to navigate to ${uiState.recentDestination} again?")
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        showRecentDialog = false
                        viewModel.navigateToRecentDestination()
                        navController.navigate("navigate?recent=true")
                    }
                ) {
                    Text("Navigate")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showRecentDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,

    onRecentDestinationClick: () -> Unit,

    onBottomNavSelected: (BottomNavItem) -> Unit,

    onStartScan: () -> Unit,

    onActualNodeSelected: (String) -> Unit
) {

    Scaffold(
        bottomBar = {
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

            CurrentLocationCard(
                location = uiState.currentLocation
            )

            RecentDestinationCard(
                destination = uiState.recentDestination,
                onClick = onRecentDestinationClick
            )

            SystemStatusCard(
                bluetoothEnabled = uiState.bluetoothEnabled,
                scannerStatus = uiState.scannerStatus
            )

            LocalizationCard(
                predictedNode = uiState.predictedNode,
                selectedActualNode = uiState.selectedActualNode,
                onActualNodeSelected = onActualNodeSelected,
                onStartScan = onStartScan
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {

    TracerTheme {

        HomeScreenContent(
            uiState = HomeUiState(),

            onRecentDestinationClick = {},

            onBottomNavSelected = {},

            onStartScan = {},

            onActualNodeSelected = {}
        )
    }
}