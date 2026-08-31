package com.shrihari.smartcampusnavigator.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
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

import com.shrihari.smartcampusnavigator.ui.tutorial.TutorialManager
import com.shrihari.smartcampusnavigator.ui.tutorial.TutorialOverlay
import com.shrihari.smartcampusnavigator.ui.tutorial.TutorialTarget

import com.shrihari.smartcampusnavigator.ui.viewmodel.HomeViewModel


// ============================================================================
// HOME SCREEN
// ============================================================================

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    tutorialManager: TutorialManager
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showRecentDialog by remember {
        mutableStateOf(false)
    }

    // =========================================================================
    // TUTORIAL TARGET BOUNDS
    // =========================================================================

    var currentLocationBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    var recentDestinationBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    // -------------------------------------------------------------------------
    // Bottom Navigation bounds
    // -------------------------------------------------------------------------

    var scanBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    var navigateBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    // =========================================================================
    // START FIRST-LAUNCH TUTORIAL
    // =========================================================================

    LaunchedEffect(Unit) {

        if (tutorialManager.shouldShowTutorial()) {

            tutorialManager.start()
        }
    }

    // =========================================================================
    // HOME CONTENT
    // =========================================================================

    HomeScreenContent(

        uiState = uiState,

        // ---------------------------------------------------------------------
        // Current Location measurement
        // ---------------------------------------------------------------------

        currentLocationModifier =
            Modifier.onGloballyPositioned { coordinates ->

                currentLocationBounds =
                    coordinates.boundsInRoot()
            },

        // ---------------------------------------------------------------------
        // Recent Destination measurement
        // ---------------------------------------------------------------------

        recentDestinationModifier =
            Modifier.onGloballyPositioned { coordinates ->

                recentDestinationBounds =
                    coordinates.boundsInRoot()
            },

        // ---------------------------------------------------------------------
        // Bottom Navigation measurement
        // ---------------------------------------------------------------------

        onBottomNavBoundsChanged = { item, bounds ->

            when (item) {

                BottomNavItem.Scan -> {

                    scanBounds = bounds
                }

                BottomNavItem.Navigate -> {

                    navigateBounds = bounds
                }

                else -> {
                    // We don't currently need Home or Settings
                }
            }
        },

        // ---------------------------------------------------------------------
        // Recent destination click
        // ---------------------------------------------------------------------

        onRecentDestinationClick = {

            if (
                uiState.recentDestination !=
                "No recent destination"
            ) {

                showRecentDialog = true
            }
        },

        // ---------------------------------------------------------------------
        // Bottom navigation
        // ---------------------------------------------------------------------

        onBottomNavSelected = { item ->

            when (item) {

                BottomNavItem.Home -> {

                    navController.navigate(
                        Screen.Home.route
                    )
                }

                BottomNavItem.Scan -> {

                    navController.navigate(
                        Screen.Scan.route
                    )
                }

                BottomNavItem.Navigate -> {

                    viewModel.navigateToRecentDestination()

                    navController.navigate(
                        Screen.Navigate.route
                    )
                }

                BottomNavItem.Settings -> {

                    navController.navigate(
                        Screen.Settings.route
                    )
                }
            }
        },

        // ---------------------------------------------------------------------
        // Start scan
        // ---------------------------------------------------------------------

        onStartScan = {

            viewModel.onStartScan()
        },

        // ---------------------------------------------------------------------
        // Actual node selected
        // ---------------------------------------------------------------------

        onActualNodeSelected = {

            viewModel.onActualNodeSelected(it)
        }
    )

    // =========================================================================
    // RECENT DESTINATION DIALOG
    // =========================================================================

    if (showRecentDialog) {

        AlertDialog(

            onDismissRequest = {

                showRecentDialog = false
            },

            title = {

                Text(
                    text = "Navigate again?"
                )
            },

            text = {

                Text(
                    text =
                        "Do you want to navigate to " +
                                "${uiState.recentDestination} again?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        showRecentDialog = false

                        viewModel.navigateToRecentDestination()

                        navController.navigate(
                            "navigate?recent=true"
                        )
                    }
                ) {

                    Text(
                        text = "Navigate"
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        showRecentDialog = false
                    }
                ) {

                    Text(
                        text = "Cancel"
                    )
                }
            }
        )
    }

    // =========================================================================
    // TUTORIAL OVERLAY
    // =========================================================================

    if (tutorialManager.isVisible) {

        // ---------------------------------------------------------------------
        // Determine which element should be highlighted
        // ---------------------------------------------------------------------

        val targetBounds = when (
            tutorialManager.currentStep.target
        ) {

            // ---------------------------------------------------------------
            // Step 1
            // ---------------------------------------------------------------

            TutorialTarget.NONE -> {

                Rect.Zero
            }

            // ---------------------------------------------------------------
            // Step 2
            // ---------------------------------------------------------------

            TutorialTarget.CURRENT_LOCATION -> {

                currentLocationBounds
            }

            // ---------------------------------------------------------------
            // Step 3
            // ---------------------------------------------------------------

            TutorialTarget.RECENT_DESTINATION -> {

                recentDestinationBounds
            }

            // ---------------------------------------------------------------
            // Step 4
            // ---------------------------------------------------------------

            TutorialTarget.SCAN -> {

                scanBounds
            }

            // ---------------------------------------------------------------
            // Step 5
            // ---------------------------------------------------------------

            TutorialTarget.NAVIGATE -> {

                navigateBounds
            }

            // ---------------------------------------------------------------
            // Step 6
            // ---------------------------------------------------------------

            TutorialTarget.QR_HANDOFF -> {

                /*
                 * QR handoff belongs to ScanScreen.
                 *
                 * It will be connected when the tutorial
                 * is allowed to move between screens.
                 *
                 * For now there is no HomeScreen target.
                 */

                Rect.Zero
            }
        }

        // ---------------------------------------------------------------------
        // Display tutorial
        // ---------------------------------------------------------------------

        TutorialOverlay(

            step =
                tutorialManager.currentStep,

            stepNumber =
                tutorialManager.currentStepNumber,

            totalSteps =
                tutorialManager.totalSteps,

            targetBounds =
                targetBounds,

            onNext = {

                tutorialManager.next()
            },

            onBack = {

                tutorialManager.back()
            },

            onSkip = {

                tutorialManager.skip()
            }
        )
    }
}


// ============================================================================
// HOME SCREEN CONTENT
// ============================================================================

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,

    currentLocationModifier: Modifier = Modifier,

    recentDestinationModifier: Modifier = Modifier,

    onBottomNavBoundsChanged: (
        BottomNavItem,
        Rect
    ) -> Unit = { _, _ -> },

    onRecentDestinationClick: () -> Unit,

    onBottomNavSelected: (BottomNavItem) -> Unit,

    onStartScan: () -> Unit,

    onActualNodeSelected: (String) -> Unit
) {

    Scaffold(

        // =========================================================================
        // BOTTOM NAVIGATION
        // =========================================================================

        bottomBar = {

            BottomNavBar(

                selectedItem =
                    uiState.selectedBottomNav,

                onItemSelected =
                    onBottomNavSelected,

                // -------------------------------------------------------------
                // Send real navigation-item coordinates to tutorial
                // -------------------------------------------------------------

                onItemBoundsChanged =
                    onBottomNavBoundsChanged
            )
        }

    ) { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(16.dp),

            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            // =================================================================
            // TOP BAR
            // =================================================================

            TracerTopBar(

                title =
                    "Tracer",

                subtitle =
                    "Indoor Navigation for Smart Campuses",

                logo =
                    painterResource(
                        id = R.drawable.tracer_logo
                    )
            )

            // =================================================================
            // WELCOME BANNER
            // =================================================================

            WelcomeBanner(

                title =
                    "Welcome",

                message =
                    "Ready to navigate your campus?"
            )

            // =================================================================
            // CURRENT LOCATION
            // =================================================================

            Box(

                modifier =
                    currentLocationModifier
                        .fillMaxWidth()

            ) {

                CurrentLocationCard(

                    location =
                        uiState.currentLocation
                )
            }

            // =================================================================
            // RECENT DESTINATION
            // =================================================================

            Box(

                modifier =
                    recentDestinationModifier
                        .fillMaxWidth()

            ) {

                RecentDestinationCard(

                    destination =
                        uiState.recentDestination,

                    onClick =
                        onRecentDestinationClick
                )
            }

            // =================================================================
            // SYSTEM STATUS
            // =================================================================

            SystemStatusCard(

                bluetoothEnabled =
                    uiState.bluetoothEnabled,

                scannerStatus =
                    uiState.scannerStatus
            )

            // =================================================================
            // LOCALIZATION
            // =================================================================

            LocalizationCard(

                predictedNode =
                    uiState.predictedNode,

                selectedActualNode =
                    uiState.selectedActualNode,

                onActualNodeSelected =
                    onActualNodeSelected,

                onStartScan =
                    onStartScan
            )
        }
    }
}


// ============================================================================
// PREVIEW
// ============================================================================

@Preview(
    showBackground = true
)
@Composable
private fun HomePreview() {

    TracerTheme {

        HomeScreenContent(

            uiState =
                HomeUiState(),

            currentLocationModifier =
                Modifier,

            recentDestinationModifier =
                Modifier,

            onBottomNavBoundsChanged =
                { _, _ -> },

            onRecentDestinationClick = {},

            onBottomNavSelected = {},

            onStartScan = {},

            onActualNodeSelected = {}
        )
    }
}