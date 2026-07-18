package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.ui.theme.TracerError
import com.shrihari.smartcampusnavigator.ui.theme.TracerPrimary
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme

/**
 * A reusable Status Card component for the Tracer application.
 * Displays a title and a status message with a color-coded indicator.
 *
 * @param title The primary title of the status card (e.g., "Bluetooth").
 * @param status The status message to display (e.g., "Connected", "Localization in progress...").
 * @param statusColor The color of the status indicator circle.
 * @param modifier The modifier to be applied to the card.
 */
@Composable
fun StatusCard(
    title: String,
    status: String,
    statusColor: Color,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Status Indicator
                Box(
                    modifier = Modifier
                        .padding(top = 4.dp) // Fine-tune alignment with the first line of text
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                        .semantics {
                            contentDescription = "Status indicator: $status"
                        }
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = status,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BluetoothDisabledPreview() {
    TracerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            StatusCard(
                title = "Bluetooth",
                status = "Disabled",
                statusColor = TracerError
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScannerReadyPreview() {
    TracerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            StatusCard(
                title = "BLE Scanner",
                status = "Ready to scan for nearby beacons",
                statusColor = Color(0xFF4CAF50) // TODO: Replace with theme success color when design system expands.
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentLocationPreview() {
    TracerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            StatusCard(
                title = "Current Location",
                status = "Department of Artificial Intelligence Laboratory - 3rd Floor",
                statusColor = TracerPrimary
            )
        }
    }
}
