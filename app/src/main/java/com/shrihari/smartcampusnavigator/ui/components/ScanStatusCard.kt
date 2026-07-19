package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val SuccessGreen = Color(0xFF4CAF50)

@Composable
fun ScanStatusCard(
    bluetoothEnabled: Boolean,
    permissionGranted: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Scan Status",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            StatusItem(
                label = "Bluetooth",
                value = if (bluetoothEnabled) "Enabled" else "Disabled",
                color = if (bluetoothEnabled)
                    SuccessGreen
                else
                    MaterialTheme.colorScheme.error
            )

            StatusItem(
                label = "Permission",
                value = if (permissionGranted) "Granted" else "Denied",
                color = if (permissionGranted)
                    SuccessGreen
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}