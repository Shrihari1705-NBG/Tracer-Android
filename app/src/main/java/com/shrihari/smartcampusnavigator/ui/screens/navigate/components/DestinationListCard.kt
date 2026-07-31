package com.shrihari.smartcampusnavigator.ui.screens.navigate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.Destination

@Composable
fun DestinationListCard(
    destinations: List<Destination>,
    selectedDestination: Destination?,
    onDestinationSelected: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            destinations.forEach { destination ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDestinationSelected(destination)
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 14.dp
                        ),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = destination.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )

                    Icon(
                        imageVector =
                            if (destination == selectedDestination)
                                Icons.Default.CheckCircle
                            else
                                Icons.Outlined.RadioButtonUnchecked,

                        contentDescription = null,

                        tint =
                            if (destination == selectedDestination)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.outline
                    )
                }

                if (destination != destinations.last()) {
                    Divider()
                }
            }
        }
    }
}