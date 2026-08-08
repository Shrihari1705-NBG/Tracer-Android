package com.shrihari.smartcampusnavigator.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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