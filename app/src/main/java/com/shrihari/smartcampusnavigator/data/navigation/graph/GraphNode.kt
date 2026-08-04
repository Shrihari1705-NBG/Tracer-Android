package com.shrihari.smartcampusnavigator.data.navigation.graph

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class GraphNode(

    val id: String,

    position: Offset,

    neighbors: List<String> = emptyList()

) {

    var position by mutableStateOf(position)

    var isSelected by mutableStateOf(false)

    val neighbors = mutableStateListOf<String>().apply {
        addAll(neighbors)
    }

}