package com.shrihari.smartcampusnavigator.ui.tutorial

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp

/**
 * Full-screen Tracer tutorial overlay.
 *
 * Displays:
 *  - Darkened background
 *  - Spotlight around the selected UI element
 *  - Highlight border
 *  - Tutorial information card
 *  - Skip / Back / Next controls
 *
 * Steps 1–3:
 *  Tutorial card remains at the bottom.
 *
 * Steps 4–6:
 *  Tutorial card is moved upward so that it does not
 *  cover too much of the Home screen.
 */
@Composable
fun TutorialOverlay(
    step: TutorialStep,
    stepNumber: Int,
    totalSteps: Int,
    targetBounds: Rect,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {

    /*
     * ---------------------------------------------------------
     * Tutorial Card Position
     * ---------------------------------------------------------
     *
     * Steps 1–3 stay at the bottom.
     *
     * Steps 4–6 move upward.
     *
     * This gives the user a better view of the Home screen
     * while the later tutorial steps are being displayed.
     *
     * ---------------------------------------------------------
     */

    val cardBottomPadding =
        if (stepNumber >= 4) {
            180.dp
        } else {
            20.dp
        }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        // =========================================================
        // SPOTLIGHT OVERLAY
        // =========================================================

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    compositingStrategy =
                        CompositingStrategy.Offscreen
                }
        ) {

            // -----------------------------------------------------
            // Darken the entire screen
            // -----------------------------------------------------

            drawRect(
                color = Color.Black.copy(alpha = 0.68f)
            )

            // -----------------------------------------------------
            // Create transparent spotlight
            // -----------------------------------------------------

            if (
                targetBounds.width > 0f &&
                targetBounds.height > 0f
            ) {

                val spotlightPadding = 10.dp.toPx()

                val spotlightRect = Rect(
                    left =
                        targetBounds.left -
                                spotlightPadding,

                    top =
                        targetBounds.top -
                                spotlightPadding,

                    right =
                        targetBounds.right +
                                spotlightPadding,

                    bottom =
                        targetBounds.bottom +
                                spotlightPadding
                )

                // -------------------------------------------------
                // Transparent spotlight
                // -------------------------------------------------

                drawRoundRect(
                    color = Color.Transparent,

                    topLeft = spotlightRect.topLeft,

                    size = spotlightRect.size,

                    cornerRadius = CornerRadius(
                        18.dp.toPx(),
                        18.dp.toPx()
                    ),

                    blendMode = BlendMode.Clear
                )

                // -------------------------------------------------
                // Spotlight border
                // -------------------------------------------------

                drawRoundRect(
                    color = Color.White,

                    topLeft = spotlightRect.topLeft,

                    size = spotlightRect.size,

                    cornerRadius = CornerRadius(
                        18.dp.toPx(),
                        18.dp.toPx()
                    ),

                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 3.dp.toPx()
                    )
                )
            }
        }

        // =========================================================
        // SKIP BUTTON
        // =========================================================

        TextButton(
            onClick = onSkip,

            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 24.dp,
                    end = 16.dp
                )
        ) {

            Text(
                text = "Skip",
                color = Color.White
            )
        }

        // =========================================================
        // TUTORIAL INFORMATION CARD
        // =========================================================

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = cardBottomPadding
                ),

            shape = RoundedCornerShape(24.dp),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),

                verticalArrangement =
                    Arrangement.spacedBy(14.dp)
            ) {

                // -------------------------------------------------
                // Step Indicator
                // -------------------------------------------------

                Text(
                    text =
                        "Step $stepNumber of $totalSteps",

                    style =
                        MaterialTheme.typography.labelMedium,

                    color =
                        MaterialTheme.colorScheme.primary
                )

                // -------------------------------------------------
                // Tutorial Title
                // -------------------------------------------------

                Text(
                    text = step.title,

                    style =
                        MaterialTheme.typography.headlineSmall
                )

                // -------------------------------------------------
                // Tutorial Description
                // -------------------------------------------------

                Text(
                    text = step.description,

                    style =
                        MaterialTheme.typography.bodyLarge
                )

                // -------------------------------------------------
                // Navigation Buttons
                // -------------------------------------------------

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    // ---------------------------------------------
                    // Back Button
                    // ---------------------------------------------

                    if (stepNumber > 1) {

                        TextButton(
                            onClick = onBack
                        ) {

                            Text("Back")
                        }

                    } else {

                        // Keeps button spacing consistent
                        Box(
                            modifier = Modifier
                        )
                    }

                    // ---------------------------------------------
                    // Next / Done Button
                    // ---------------------------------------------

                    Button(
                        onClick = onNext
                    ) {

                        Text(
                            text =
                                if (
                                    stepNumber == totalSteps
                                ) {
                                    "Done"
                                } else {
                                    "Next"
                                }
                        )
                    }
                }
            }
        }
    }
}