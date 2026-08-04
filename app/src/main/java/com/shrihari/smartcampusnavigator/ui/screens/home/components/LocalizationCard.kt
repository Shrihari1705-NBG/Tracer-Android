package com.shrihari.smartcampusnavigator.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalizationCard(

    predictedNode: String,

    selectedActualNode: String,

    onActualNodeSelected: (String) -> Unit,

    onStartScan: () -> Unit = {}

) {

    val nodes = listOf(
        "N13","N14","N15","N16","N17",
        "N18","N19","N20","N21","N22",
        "N23","N24","N25","N26","N27"
    )

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedNode by remember {
        mutableStateOf(nodes.first())
    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            Text(
                text = "Localization",
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onStartScan
            ) {
                Text("Start Scan")
            }

            ExposedDropdownMenuBox(

                expanded = expanded,

                onExpandedChange = {
                    expanded = !expanded
                }

            ) {

                OutlinedTextField(

                    value = selectedNode,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Actual Node")
                    },

                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },

                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()

                )

                ExposedDropdownMenu(

                    expanded = expanded,

                    onDismissRequest = {
                        expanded = false
                    }

                ) {

                    nodes.forEach { node ->

                        DropdownMenuItem(

                            text = {
                                Text(node)
                            },

                            onClick = {

                                selectedNode = node

                                expanded = false

                            }

                        )

                    }

                }

            }

            Text(
                text = "Predicted Node",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = predictedNode
            )

        }

    }

}