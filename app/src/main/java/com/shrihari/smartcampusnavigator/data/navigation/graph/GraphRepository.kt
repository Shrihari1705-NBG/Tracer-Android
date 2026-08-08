package com.shrihari.smartcampusnavigator.data.navigation.graph


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset

object GraphRepository {

    val nodes = mutableStateListOf(

        GraphNode(
            id = "N1",
            position = Offset(741f, 134f),
            neighbors = listOf("N2","N10")
        ),

        GraphNode(
            id = "N2",
            position = Offset(772f, 209f),
            neighbors = listOf("N1", "N3", "N4", "N6", "N10")
        ),

        GraphNode(
            id = "N3",
            position = Offset(881f, 178f),
            neighbors = listOf("N2", "N4", "N6")
        ),

        GraphNode(
            id = "N4",
            position = Offset(881f, 347f),
            neighbors = listOf("N2", "N3")
        ),

        GraphNode(
            id = "N5",
            position = Offset(1097f, 172f),
            neighbors = listOf("N28")
        ),

        GraphNode(
            id = "N6",
            position = Offset(988f, 228f),
            neighbors = listOf("N2", "N3", "N28")
        ),

        GraphNode(
            id = "N7",
            position = Offset(1134f, 328f),
            neighbors = listOf("N28", "N8")
        ),

        GraphNode(
            id = "N8",
            position = Offset(1134f, 403f),
            neighbors = listOf("N7", "N9")
        ),

        GraphNode(
            id = "N9",
            position = Offset(1091f, 534f),
            neighbors = listOf("N8")
        ),

        GraphNode(
            id = "N10",
            position = Offset(678f, 209f),
            neighbors = listOf("N1", "N2", "N11", "N12")
        ),

        GraphNode(
            id = "N11",
            position = Offset(653f, 122f),
            neighbors = listOf("N10")
        ),

        GraphNode(
            id = "N12",
            position = Offset(553f, 322f),
            neighbors = listOf("N10", "N13", "N14")
        ),

        GraphNode(
            id = "N13",
            position = Offset(350f, 259f),
            neighbors = listOf("N12", "N14", "N15", "N16", "N27")
        ),

        GraphNode(
            id = "N14",
            position = Offset(316f, 372f),
            neighbors = listOf("N12", "N13", "N18", "N19", "N21", "N22", "N27")
        ),

        GraphNode(
            id = "N15",
            position = Offset(384f, 209f),
            neighbors = listOf("N13")
        ),

        GraphNode(
            id = "N16",
            position = Offset(331f, 103f),
            neighbors = listOf("N13", "N17")
        ),

        GraphNode(
            id = "N17",
            position = Offset(331f, 34f),
            neighbors = listOf("N16")
        ),

        GraphNode(
            id = "N18",
            position = Offset(369f, 447f),
            neighbors = listOf("N14", "N21", "N22")
        ),

        GraphNode(
            id = "N19",
            position = Offset(340f, 556f),
            neighbors = listOf("N14", "N20")
        ),

        GraphNode(
            id = "N20",
            position = Offset(340f, 616f),
            neighbors = listOf("N19")
        ),

        GraphNode(
            id = "N21",
            position = Offset(200f, 506f),
            neighbors = listOf("N14", "N18")
        ),

        GraphNode(
            id = "N22",
            position = Offset(222f, 403f),
            neighbors = listOf("N14", "N18", "N23", "N25", "N26")
        ),

        GraphNode(
            id = "N23",
            position = Offset(131f, 390f),
            neighbors = listOf("N22", "N24", "N25", "N26")
        ),

        GraphNode(
            id = "N24",
            position = Offset(81f, 356f),
            neighbors = listOf("N23", "N25", "N26")
        ),

        GraphNode(
            id = "N25",
            position = Offset(141f, 309f),
            neighbors = listOf("N22", "N23", "N24")
        ),

        GraphNode(
            id = "N26",
            position = Offset(200f, 312f),
            neighbors = listOf("N22", "N23", "N24")
        ),

        GraphNode(
            id = "N27",
            position = Offset(206f, 222f),
            neighbors = listOf("N13", "N14")
        ),

        GraphNode(
            id = "N28",
            position = Offset(1068f, 200f),
            neighbors = listOf("N5", "N6", "N7")
        )

    )
}