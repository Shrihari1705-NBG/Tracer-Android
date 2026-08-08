package com.shrihari.smartcampusnavigator.ui.screens.navigate.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.data.navigation.graph.GraphNode

@Composable
fun ZoomableMap(
    route: List<GraphNode>,
    modifier: Modifier = Modifier
) {

    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val pathProgress = rememberPathAnimation(route.size)

    // ---------------------------------------------------------
    // Auto-frame the current route whenever it changes
    // ---------------------------------------------------------

    LaunchedEffect(route) {

        if (route.size < 2) {
            scale = 1f
            offset = Offset.Zero
            return@LaunchedEffect
        }

        val minX = route.minOf { it.position.x }
        val maxX = route.maxOf { it.position.x }
        val minY = route.minOf { it.position.y }
        val maxY = route.maxOf { it.position.y }

        val routeWidth = maxX - minX
        val routeHeight = maxY - minY

        val paddedWidth = routeWidth + 160f
        val paddedHeight = routeHeight + 160f

        val mapWidth = 1280f
        val mapHeight = 720f

        val zoomX = mapWidth / paddedWidth
        val zoomY = mapHeight / paddedHeight

        scale = minOf(zoomX, zoomY).coerceIn(1.4f, 3.8f)

        val centerX = (minX + maxX) / 2f
        val centerY = (minY + maxY) / 2f

        offset = Offset(
            (mapWidth / 2f - centerX) * scale,
            (mapHeight / 2f - centerY) * scale
        )
    }

    val animatedScale by animateFloatAsState(
        targetValue = scale,
        label = "MapScale"
    )

    val transformState = rememberTransformableState { zoomChange, panChange, _ ->

        scale = (scale * zoomChange).coerceIn(1f, 5f)

        if (scale == 1f) {
            offset = Offset.Zero
        } else {
            offset += panChange
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (scale > 1f) {
                            scale = 1f
                            offset = Offset.Zero
                        } else {
                            scale = 2f
                        }
                    }
                )
            }
            .transformable(transformState),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1280f / 720f)
                .graphicsLayer {

                    scaleX = animatedScale
                    scaleY = animatedScale

                    val maxTranslationX = (1280f * animatedScale - 1280f) / 2f
                    val maxTranslationY = (720f * animatedScale - 720f) / 2f

                    translationX = offset.x.coerceIn(
                        -maxTranslationX,
                        maxTranslationX
                    )

                    translationY = offset.y.coerceIn(
                        -maxTranslationY,
                        maxTranslationY
                    )
                }
        ) {

            Image(
                painter = painterResource(R.drawable.department_map),
                contentDescription = "Department Map",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {

                val scaleX = size.width / 1280f
                val scaleY = size.height / 720f

                // ---------------------------------------------------------
                // Animated smooth route
                // ---------------------------------------------------------

                if (route.size >= 2) {

                    val path = Path()

                    val first = route.first().position

                    path.moveTo(
                        first.x * scaleX,
                        first.y * scaleY
                    )

                    for (i in 1 until route.size) {

                        val previous = route[i - 1].position
                        val current = route[i].position

                        val midX = (previous.x + current.x) / 2f
                        val midY = (previous.y + current.y) / 2f

                        path.quadraticBezierTo(
                            previous.x * scaleX,
                            previous.y * scaleY,
                            midX * scaleX,
                            midY * scaleY
                        )
                    }

                    val last = route.last().position

                    path.lineTo(
                        last.x * scaleX,
                        last.y * scaleY
                    )

                    val measure = PathMeasure()

                    measure.setPath(path, false)

                    val animatedPath = Path()

                    measure.getSegment(
                        startDistance = 0f,
                        stopDistance = measure.length * pathProgress,
                        destination = animatedPath,
                        startWithMoveTo = true
                    )

                    // Shadow
                    drawPath(
                        path = animatedPath,
                        color = Color(0x55FF9800),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 14f,
                            cap = StrokeCap.Round
                        )
                    )

                    // Main orange route
                    drawPath(
                        path = animatedPath,
                        color = Color(0xFFFF9800),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 8f,
                            cap = StrokeCap.Round
                        )
                    )
                }

                // ---------------------------------------------------------
                // Current node (blue)
                // ---------------------------------------------------------

                if (route.isNotEmpty()) {

                    val current = route.first().position

                    drawCircle(
                        color = Color.White,
                        radius = 14f,
                        center = Offset(
                            current.x * scaleX,
                            current.y * scaleY
                        )
                    )

                    drawCircle(
                        color = Color(0xFF1565C0),
                        radius = 10f,
                        center = Offset(
                            current.x * scaleX,
                            current.y * scaleY
                        )
                    )
                }

                // ---------------------------------------------------------
                // Destination node (red)
                // ---------------------------------------------------------

                if (route.size >= 2) {

                    val destination = route.last().position

                    drawCircle(
                        color = Color(0x55FF9800),
                        radius = 18f,
                        center = Offset(
                            destination.x * scaleX,
                            destination.y * scaleY
                        )
                    )

                    drawCircle(
                        color = Color.White,
                        radius = 12f,
                        center = Offset(
                            destination.x * scaleX,
                            destination.y * scaleY
                        )
                    )

                    drawCircle(
                        color = Color.Red,
                        radius = 8f,
                        center = Offset(
                            destination.x * scaleX,
                            destination.y * scaleY
                        )
                    )
                }
            }
        }
    }
}