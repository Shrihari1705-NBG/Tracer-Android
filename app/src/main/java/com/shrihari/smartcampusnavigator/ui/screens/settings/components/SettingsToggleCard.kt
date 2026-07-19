package com.shrihari.smartcampusnavigator.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.ApplicationCard
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.DevelopersCard
import com.shrihari.smartcampusnavigator.ui.screens.settings.components.GuideCard

@Composable
fun SettingsToggleCard(
    darkThemeEnabled: Boolean,
    bluetoothEnabled: Boolean,
    locationEnabled: Boolean,
    onDarkThemeChanged: (Boolean) -> Unit,
    onBluetoothChanged: (Boolean) -> Unit,
    onLocationChanged: (Boolean) -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            ToggleRow(
                title = "Dark Theme",
                checked = darkThemeEnabled,
                onCheckedChange = onDarkThemeChanged
            )

            ToggleRow(
                title = "Bluetooth",
                checked = bluetoothEnabled,
                onCheckedChange = onBluetoothChanged
            )

            ToggleRow(
                title = "Location",
                checked = locationEnabled,
                onCheckedChange = onLocationChanged
            )

        }

    }

}




@Composable
private fun ToggleRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Switch(
            checked = checked,
            onCheckedChange = {
                android.util.Log.d("SWITCH_TEST", "$title clicked -> $it")
                onCheckedChange(it)
            },
            enabled = true
        )

    }

}