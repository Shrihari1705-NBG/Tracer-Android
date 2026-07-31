package com.shrihari.smartcampusnavigator.ui.screens.navigate.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shrihari.smartcampusnavigator.R

@Composable
fun ZoomableMap(
    modifier: Modifier = Modifier
) {

    var scale by remember {
        mutableStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset.Zero)
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

    ){

        Image(
            painter = painterResource(R.drawable.department_map),
            contentDescription = "Department Map",

            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {

                    scaleX = animatedScale
                    scaleY = animatedScale

                    translationX = offset.x
                    translationY = offset.y

                },

            contentScale = ContentScale.Fit
        )
    }
}