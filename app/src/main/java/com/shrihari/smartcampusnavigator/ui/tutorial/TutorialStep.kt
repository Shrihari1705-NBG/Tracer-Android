package com.shrihari.smartcampusnavigator.ui.tutorial

/**
 * Identifies the UI element that a tutorial step should highlight.
 */
enum class TutorialTarget {

    NONE,

    CURRENT_LOCATION,

    RECENT_DESTINATION,

    SCAN,

    NAVIGATE,

    QR_HANDOFF
}

/**
 * Represents one step in the Tracer first-launch walkthrough.
 */
data class TutorialStep(

    val title: String,

    val description: String,

    val target: TutorialTarget = TutorialTarget.NONE

)