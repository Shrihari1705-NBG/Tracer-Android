package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.data.model.BleDevice

@Composable
fun BeaconListCard(
    devices: List<BleDevice>,
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
                text = "Nearby Beacons",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            devices.forEachIndexed { index, device ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = device.name ?: "Unknown Device",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "${device.rssi} dBm",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (index != devices.lastIndex) {
                    Divider()
                }
            }
        }
    }
}