package com.shrihari.smartcampusnavigator.data.navigation

object NodeNameMapper {

    private val nodeNames = mapOf(
        "N1" to "HOD ECE",
        "N2" to "Main Corridor",
        "N3" to "Analog Lab",
        "N4" to "DSP Lab",
        "N5" to "Research Lab",
        "N6" to "Department Library",
        "N7" to "Faculty Cabin Area",
        "N8" to "Faculty Cabin Area",
        "N9" to "Faculty Cabin Area",
        "N10" to "Central Junction",
        "N11" to "Ladies Washroom",
        "N12" to "Left Wing Junction",
        "N13" to "Left Wing Entrance",
        "N14" to "Left Wing Corridor",
        "N15" to "Ladies Room",
        "N16" to "Faculty Cabin Area",
        "N17" to "Faculty Cabin Area",
        "N18" to "DC Lab",
        "N19" to "Faculty Cabin Area",
        "N20" to "Faculty Cabin Area",
        "N21" to "Block 6 (Smart Room)",
        "N22" to "Block 6 Corridor",
        "N23" to "Faculty Cabin Area",
        "N24" to "Faculty Cabin Area",
        "N25" to "Faculty Cabin Area",
        "N26" to "Faculty Cabin Area",
        "N27" to "Block 6(A)",
        "N28" to "Right Wing Junction"
    )

    fun getDisplayName(nodeId: String): String {
        val name = nodeNames[nodeId] ?: "Unknown Location"
        return "$name ($nodeId)"
    }
}