package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme

/**
 * A reusable Hero Banner component for the Tracer application.
 * Designed to provide a prominent introduction or status update on a screen.
 *
 * @param title The text to be displayed as the main heading.
 * @param message The supporting text to be displayed below the title.
 * @param modifier The modifier to be applied to the banner.
 * @param enabled Whether the banner is in its active state. When false, the banner is dimmed.
 */
@Composable
fun WelcomeBanner(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .alpha(if (enabled) 1f else 0.5f)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.semantics { heading() }
            )
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeBannerPreview() {
    TracerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            WelcomeBanner(
                title = "Welcome",
                message = "Ready to navigate your campus?"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeBannerDisabledPreview() {
    TracerTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            WelcomeBanner(
                title = "Services Offline",
                message = "Please enable Bluetooth to continue.",
                enabled = false
            )
        }
    }
}
