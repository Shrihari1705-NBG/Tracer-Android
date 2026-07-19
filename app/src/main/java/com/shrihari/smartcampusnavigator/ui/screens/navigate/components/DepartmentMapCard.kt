package com.shrihari.smartcampusnavigator.ui.screens.navigate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset
import com.shrihari.smartcampusnavigator.R
import androidx.compose.runtime.mutableStateOf

@Composable
fun DepartmentMapCard(
    modifier: Modifier = Modifier
) {

    // -----------------------------
    // Transformation States
    // -----------------------------

    var scale by remember {
        mutableFloatStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    // -----------------------------
    // Card
    // -----------------------------

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Text(
            text = "Department Layout",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // -----------------------------
        // Viewport
        // -----------------------------

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {

            // -----------------------------
            // Map Layer
            // Everything placed here
            // will move together.
            // -----------------------------

            Box(

                modifier = Modifier

                    .graphicsLayer {

                        scaleX = scale
                        scaleY = scale

                        translationX = offset.x
                        translationY = offset.y
                    }

                    .pointerInput(Unit) {

                        detectTapGestures(

                            onDoubleTap = {

                                scale = 1f
                                offset = Offset.Zero

                            }

                        )

                    }

                    .pointerInput(Unit) {

                        detectTransformGestures { _, pan, zoom, _ ->

                            val newScale = (scale * zoom).coerceIn(1f, 5f)

                            scale = newScale

                            offset += pan

                        }

                    }

            ) {

                // -----------------------------
                // Department Layout Image
                // -----------------------------

                Image(

                    painter = painterResource(R.drawable.department_map),

                    contentDescription = "ECE Department Layout",

                    modifier = Modifier.fillMaxWidth(),

                    contentScale = ContentScale.Fit

                )

                /*
                 =========================================================

                 Future Components

                 Place everything INSIDE this Box

                 so they zoom together with the map.

                 Example:

                 🔵 Current Location Marker

                 📍 Destination Marker

                 Canvas {
                     drawPath(...)
                 }

                 =========================================================
                */

            }

        }

    }

}