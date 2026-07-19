package com.shrihari.smartcampusnavigator.ui.screens.navigate

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
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.PrimaryButton
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar
import com.shrihari.smartcampusnavigator.ui.navigation.Screen
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.CurrentLocationCard
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.DepartmentMapCard
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.DestinationCard

@Composable
fun NavigateScreen(
    navController: NavController,
    viewModel: NavigateViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    NavigateScreenContent(
        navController = navController,
        uiState = uiState.value
    )
}

@Composable
private fun NavigateScreenContent(
    navController: NavController,
    uiState: NavigateUiState
) {

    Scaffold(

        bottomBar = {

            BottomNavBar(
                selectedItem = BottomNavItem.Navigate,
                onItemSelected = { item ->

                    when (item) {

                        BottomNavItem.Home ->
                            navController.navigate(Screen.Home.route)

                        BottomNavItem.Scan ->
                            navController.navigate(Screen.Scan.route)

                        BottomNavItem.Navigate ->
                            navController.navigate(Screen.Navigate.route)

                        BottomNavItem.Settings -> {
                            // Coming Soon
                        }
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

            CurrentLocationCard(
                currentLocation = uiState.currentLocation
            )

            DestinationCard(
                destination = uiState.destination
            )

            DepartmentMapCard()

            PrimaryButton(
                text = "Start Navigation",
                onClick = {
                    // A* Navigation will be implemented later
                }
            )
        }
    }
}