package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme

/**
 * A reusable Primary Button component for the Tracer application.
 * Designed for the main actions on a screen with high visibility and tactile feedback.
 *
 * @param text The text to be displayed on the button.
 * @param onClick The callback to be invoked when the button is clicked.
 * @param modifier The modifier to be applied to the button.
 * @param enabled Whether the button is interactive.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonEnabledPreview() {
    TracerTheme {
        PrimaryButton(
            text = "Start Scan",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonDisabledPreview() {
    TracerTheme {
        PrimaryButton(
            text = "Start Scan",
            onClick = {},
            enabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonLongTextPreview() {
    TracerTheme {
        PrimaryButton(
            text = "Start Indoor Navigation",
            onClick = {}
        )
    }
}
