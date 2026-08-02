package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeSelectionCard(
    selectedNode: String,
    nodes: List<String>,
    onNodeSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
            modifier = Modifier.padding(16.dp)
        ) {

            OutlinedTextField(
                value = selectedNode,
                onValueChange = {},
                readOnly = true,

                label = {
                    Text("Current Node")
                },

                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
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

                            onNodeSelected(node)
                            expanded = false

                        }
                    )

                }

            }

        }

    }

}