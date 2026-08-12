package com.shrihari.smartcampusnavigator.ui.screens.scan

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(
    navController: NavController
) {

    val context = LocalContext.current

    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->

        val contents = result.contents

        if (!contents.isNullOrBlank()) {

            val uri = Uri.parse(contents)

            if (uri.scheme == "tracer" && uri.host == "navigate") {

                val destination = uri.getQueryParameter("destination")

                navController.navigate(
                    "navigate?recent=false&destination=${destination ?: ""}"
                ) {
                    launchSingleTop = true
                    restoreState = true
                }

            } else {

                navController.popBackStack()

            }

        } else {

            navController.popBackStack()

        }

    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {

            val options = ScanOptions().apply {
                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                setPrompt("Scan the QR code displayed on Tracer Kiosk")
                setBeepEnabled(true)
                setOrientationLocked(true)
                captureActivity = PortraitCaptureActivity::class.java
            }

            scannerLauncher.launch(options)

        } else {

            navController.popBackStack()

        }

    }

    LaunchedEffect(Unit) {

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val options = ScanOptions().apply {
                setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                setPrompt("Scan the QR code displayed on Tracer Kiosk")
                setBeepEnabled(true)
                setOrientationLocked(true)
                captureActivity = PortraitCaptureActivity::class.java
            }

            scannerLauncher.launch(options)

        } else {

            permissionLauncher.launch(Manifest.permission.CAMERA)

        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("QR Scanner") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Point your camera at the QR code displayed on the Tracer Kiosk.",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = {

                    val options = ScanOptions().apply {
                        setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                        setPrompt("Scan the QR code displayed on Tracer Kiosk")
                        setBeepEnabled(true)
                        setOrientationLocked(true)
                        captureActivity = PortraitCaptureActivity::class.java
                    }

                    scannerLauncher.launch(options)

                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("Open Camera Again")
            }

        }

    }

}
