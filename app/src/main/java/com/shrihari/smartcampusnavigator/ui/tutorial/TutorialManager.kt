package com.shrihari.smartcampusnavigator.ui.tutorial

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Controls the state of the Tracer first-launch tutorial.
 *
 * The manager handles:
 * - Current tutorial step
 * - Next
 * - Back
 * - Skip
 * - Completion
 * - First-launch persistence
 *
 * One TutorialManager instance should be shared by AppNavigation
 * so the tutorial state remains consistent across screens.
 */
class TutorialManager(
    context: Context
) {

    // ---------------------------------------------------------
    // Preferences
    // ---------------------------------------------------------

    private val preferences = context.getSharedPreferences(
        "tracer_tutorial",
        Context.MODE_PRIVATE
    )

    companion object {

        private const val KEY_TUTORIAL_COMPLETED =
            "tutorial_completed"
    }

    // ---------------------------------------------------------
    // Tutorial Steps
    // ---------------------------------------------------------

    val steps = listOf(

        TutorialStep(
            title = "Welcome to Tracer",
            description =
                "Tracer helps you find your way around your campus using indoor navigation.",
            target = TutorialTarget.NONE
        ),

        TutorialStep(
            title = "Know Your Location",
            description =
                "Tracer uses nearby BLE beacons to estimate your current indoor location.",
            target = TutorialTarget.CURRENT_LOCATION
        ),

        TutorialStep(
            title = "Recent Destination",
            description =
                "Your most recently selected destination is saved here so you can quickly navigate to it again.",
            target = TutorialTarget.RECENT_DESTINATION
        ),

        TutorialStep(
            title = "Scan for Beacons",
            description =
                "Use the Scan section to detect nearby Tracer beacons and collect RSSI fingerprint data.",
            target = TutorialTarget.SCAN
        ),

        TutorialStep(
            title = "Find Your Destination",
            description =
                "Open Navigate, choose your destination, and follow the route shown on the indoor map.",
            target = TutorialTarget.NAVIGATE
        ),

        TutorialStep(
            title = "Continue From a Tracer Kiosk",
            description =
                "Scan the QR code displayed on a Tracer Kiosk to continue your selected navigation on your phone.",
            target = TutorialTarget.QR_HANDOFF
        )
    )

    // ---------------------------------------------------------
    // State
    // ---------------------------------------------------------

    var currentStepIndex by mutableStateOf(0)
        private set

    var isVisible by mutableStateOf(false)
        private set

    var isCompleted by mutableStateOf(
        preferences.getBoolean(
            KEY_TUTORIAL_COMPLETED,
            false
        )
    )
        private set

    // ---------------------------------------------------------
    // Current Step
    // ---------------------------------------------------------

    val currentStep: TutorialStep
        get() = steps[currentStepIndex]

    val currentStepNumber: Int
        get() = currentStepIndex + 1

    val totalSteps: Int
        get() = steps.size

    // ---------------------------------------------------------
    // Check Whether Tutorial Should Start
    // ---------------------------------------------------------

    fun shouldShowTutorial(): Boolean {
        return !isCompleted
    }

    // ---------------------------------------------------------
    // Start Tutorial
    // ---------------------------------------------------------

    fun start() {

        if (isCompleted) {
            return
        }

        currentStepIndex = 0
        isVisible = true
    }

    // ---------------------------------------------------------
    // Next
    // ---------------------------------------------------------

    fun next() {

        if (!isVisible) {
            return
        }

        if (currentStepIndex < steps.lastIndex) {

            currentStepIndex++

        } else {

            complete()
        }
    }

    // ---------------------------------------------------------
    // Back
    // ---------------------------------------------------------

    fun back() {

        if (!isVisible) {
            return
        }

        if (currentStepIndex > 0) {

            currentStepIndex--
        }
    }

    // ---------------------------------------------------------
    // Skip
    // ---------------------------------------------------------

    fun skip() {

        if (!isVisible) {
            return
        }

        complete()
    }

    // ---------------------------------------------------------
    // Complete
    // ---------------------------------------------------------

    private fun complete() {

        isCompleted = true
        isVisible = false

        preferences.edit()
            .putBoolean(
                KEY_TUTORIAL_COMPLETED,
                true
            )
            .apply()
    }

    // ---------------------------------------------------------
    // Reset Tutorial
    //
    // Used during development/testing.
    // ---------------------------------------------------------

    fun reset() {

        currentStepIndex = 0
        isCompleted = false
        isVisible = false

        preferences.edit()
            .putBoolean(
                KEY_TUTORIAL_COMPLETED,
                false
            )
            .apply()
    }
}