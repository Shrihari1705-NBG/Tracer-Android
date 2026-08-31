package com.shrihari.smartcampusnavigator.ui.screens.scan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
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
import com.shrihari.smartcampusnavigator.ui.components.SampleCounterCard
import com.shrihari.smartcampusnavigator.ui.components.ScanStatusCard
import com.shrihari.smartcampusnavigator.ui.components.TimerCard
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar

import com.shrihari.smartcampusnavigator.ui.navigation.Screen

import com.shrihari.smartcampusnavigator.ui.tutorial.TutorialManager
import com.shrihari.smartcampusnavigator.ui.tutorial.TutorialOverlay
import com.shrihari.smartcampusnavigator.ui.tutorial.TutorialTarget


// ============================================================================
// SCAN SCREEN
// ============================================================================

@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: ScanViewModel = hiltViewModel(),
    tutorialManager: TutorialManager
) {

    val uiState =
        viewModel.uiState.collectAsStateWithLifecycle()

    ScanScreenContent(

        navController = navController,

        uiState = uiState.value,

        viewModel = viewModel,

        tutorialManager = tutorialManager
    )
}


// ============================================================================
// SCAN SCREEN CONTENT
// ============================================================================

@Composable
private fun ScanScreenContent(
    navController: NavController,
    uiState: ScanUiState,
    viewModel: ScanViewModel,
    tutorialManager: TutorialManager
) {

    // =========================================================================
    // TUTORIAL TARGET BOUNDS
    // =========================================================================

    // Bottom navigation Scan item
    var scanNavBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    // Bottom navigation Navigate item
    var navigateNavBounds by remember {
        mutableStateOf(Rect.Zero)
    }

    // QR Handoff card
    var qrHandoffBounds by remember {
        mutableStateOf(Rect.Zero)
    }


    // =========================================================================
    // BLUETOOTH DIALOG
    // =========================================================================

    if (uiState.showBluetoothDialog) {

        AlertDialog(

            onDismissRequest = {

                viewModel.dismissBluetoothDialog()
            },

            title = {

                Text(
                    text = "Bluetooth Required"
                )
            },

            text = {

                Text(
                    text =
                        "Please enable Bluetooth before starting a scan."
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        viewModel.dismissBluetoothDialog()
                    }
                ) {

                    Text(
                        text = "OK"
                    )
                }
            }
        )
    }


    // =========================================================================
    // MAIN SCAFFOLD
    // =========================================================================

    Scaffold(

        // =====================================================================
        // BOTTOM NAVIGATION
        // =====================================================================

        bottomBar = {

            BottomNavBar(

                selectedItem =
                    BottomNavItem.Scan,

                onItemSelected = { item ->

                    when (item) {

                        // -----------------------------------------------------
                        // Home
                        // -----------------------------------------------------

                        BottomNavItem.Home -> {

                            navController.navigate(
                                Screen.Home.route
                            )
                        }

                        // -----------------------------------------------------
                        // Scan
                        // -----------------------------------------------------

                        BottomNavItem.Scan -> {

                            navController.navigate(
                                Screen.Scan.route
                            )
                        }

                        // -----------------------------------------------------
                        // Navigate
                        // -----------------------------------------------------

                        BottomNavItem.Navigate -> {

                            navController.navigate(
                                Screen.Navigate.route
                            )
                        }

                        // -----------------------------------------------------
                        // Settings
                        // -----------------------------------------------------

                        BottomNavItem.Settings -> {

                            navController.navigate(
                                Screen.Settings.route
                            )
                        }
                    }
                },

                // -------------------------------------------------------------
                // Tutorial bounds
                // -------------------------------------------------------------

                onItemBoundsChanged = { item, bounds ->

                    when (item) {

                        BottomNavItem.Scan -> {

                            scanNavBounds = bounds
                        }

                        BottomNavItem.Navigate -> {

                            navigateNavBounds = bounds
                        }

                        else -> {
                            // Home and Settings are not tutorial targets.
                        }
                    }
                }
            )
        }

    ) { innerPadding ->


        // =========================================================================
        // CONTENT
        // =========================================================================

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


            // =====================================================================
            // TOP BAR
            // =====================================================================

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


            // =====================================================================
            // NODE SELECTION
            // =====================================================================

            NodeSelectionCard(

                selectedNode =
                    uiState.selectedNode,

                nodes =
                    uiState.availableNodes,

                onNodeSelected =
                    viewModel::onNodeSelected
            )


            // =====================================================================
            // SCAN STATUS
            // =====================================================================

            ScanStatusCard(

                bluetoothEnabled =
                    uiState.bluetoothEnabled
            )


            // =====================================================================
            // TIMER
            // =====================================================================

            TimerCard(

                elapsedTime =
                    uiState.elapsedTime
            )


            // =====================================================================
            // SAMPLE COUNTER
            // =====================================================================

            SampleCounterCard(

                sampleCount =
                    uiState.sampleCount
            )


            // =====================================================================
            // NEARBY BEACONS
            // =====================================================================

            BeaconListCard(

                devices =
                    uiState.nearbyDevices
            )


            // =====================================================================
            // START / STOP SCAN
            // =====================================================================

            PrimaryButton(

                text =

                    if (uiState.isScanning) {

                        "Stop Scan"

                    } else {

                        "Start Scan"
                    },

                onClick = {

                    viewModel.toggleScan()
                }
            )


            // =====================================================================
            // EXPORT CSV
            // =====================================================================

            PrimaryButton(

                text =
                    "Export CSV",

                enabled =
                    uiState.sampleCount > 0,

                onClick = {

                    viewModel.exportCsv()
                }
            )


            // =====================================================================
            // QR HANDOFF
            // =====================================================================

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->

                        qrHandoffBounds =
                            coordinates.boundsInRoot()
                    },

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant
                    )
            ) {

                Column(

                    modifier =
                        Modifier.padding(16.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {


                    // -------------------------------------------------------------
                    // Title
                    // -------------------------------------------------------------

                    Text(

                        text =
                            "Continue from Tracer Kiosk",

                        style =
                            MaterialTheme.typography.titleMedium
                    )


                    // -------------------------------------------------------------
                    // Description
                    // -------------------------------------------------------------

                    Text(

                        text =
                            "Scan the QR code displayed on the Tracer Kiosk to continue navigation on your phone.",

                        style =
                            MaterialTheme.typography.bodyMedium
                    )


                    // -------------------------------------------------------------
                    // QR Scanner Button
                    // -------------------------------------------------------------

                    PrimaryButton(

                        text =
                            "Open QR Scanner",

                        onClick = {

                            navController.navigate(
                                Screen.QrScanner.route
                            )
                        }
                    )
                }
            }
        }
    }


    // =========================================================================
    // TUTORIAL OVERLAY
    // =========================================================================

    if (tutorialManager.isVisible) {

        // ---------------------------------------------------------------------
        // Current tutorial target
        // ---------------------------------------------------------------------

        val currentTarget =
            tutorialManager.currentStep.target


        // ---------------------------------------------------------------------
        // Determine spotlight bounds
        // ---------------------------------------------------------------------

        val targetBounds = when (currentTarget) {

            // ---------------------------------------------------------------
            // Step 4
            // ---------------------------------------------------------------

            TutorialTarget.SCAN -> {

                scanNavBounds
            }


            // ---------------------------------------------------------------
            // Step 5
            // ---------------------------------------------------------------

            TutorialTarget.NAVIGATE -> {

                navigateNavBounds
            }


            // ---------------------------------------------------------------
            // Step 6
            // ---------------------------------------------------------------

            TutorialTarget.QR_HANDOFF -> {

                qrHandoffBounds
            }


            // ---------------------------------------------------------------
            // Other targets
            // ---------------------------------------------------------------

            else -> {

                Rect.Zero
            }
        }


        // ---------------------------------------------------------------------
        // Tutorial Overlay
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


            // =================================================================
            // NEXT
            // =================================================================

            onNext = {

                when (currentTarget) {


                    // ---------------------------------------------------------
                    // STEP 4
                    //
                    // Scan → Navigate
                    // ---------------------------------------------------------

                    TutorialTarget.SCAN -> {

                        tutorialManager.next()

                        navController.navigate(
                            Screen.Navigate.route
                        )
                    }


                    // ---------------------------------------------------------
                    // STEP 5
                    //
                    // Navigate → QR Handoff
                    //
                    // The QR handoff is located on ScanScreen,
                    // therefore return to ScanScreen.
                    // ---------------------------------------------------------

                    TutorialTarget.NAVIGATE -> {

                        tutorialManager.next()

                        navController.navigate(
                            Screen.Scan.route
                        )
                    }


                    // ---------------------------------------------------------
                    // STEP 6
                    //
                    // QR Handoff → Complete
                    // ---------------------------------------------------------

                    TutorialTarget.QR_HANDOFF -> {

                        tutorialManager.next()
                    }


                    // ---------------------------------------------------------
                    // Other steps
                    // ---------------------------------------------------------

                    else -> {

                        tutorialManager.next()
                    }
                }
            },


            // =================================================================
            // BACK
            // =================================================================

            onBack = {

                when (currentTarget) {


                    // ---------------------------------------------------------
                    // Step 4
                    //
                    // Return to Home
                    // ---------------------------------------------------------

                    TutorialTarget.SCAN -> {

                        tutorialManager.back()

                        navController.navigate(
                            Screen.Home.route
                        )
                    }


                    // ---------------------------------------------------------
                    // Step 6
                    //
                    // Return to Navigate
                    // ---------------------------------------------------------

                    TutorialTarget.QR_HANDOFF -> {

                        tutorialManager.back()

                        navController.navigate(
                            Screen.Navigate.route
                        )
                    }


                    // ---------------------------------------------------------
                    // Default
                    // ---------------------------------------------------------

                    else -> {

                        tutorialManager.back()
                    }
                }
            },


            // =================================================================
            // SKIP
            // =================================================================

            onSkip = {

                tutorialManager.skip()
            }
        )
    }
}