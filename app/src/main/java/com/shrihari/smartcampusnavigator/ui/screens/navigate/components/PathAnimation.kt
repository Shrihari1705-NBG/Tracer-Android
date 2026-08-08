package com.shrihari.smartcampusnavigator.ui.screens.navigate.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun rememberPathAnimation(routeSize: Int): Float {

    val progress = remember { Animatable(0f) }

    LaunchedEffect(routeSize) {

        progress.snapTo(0f)

        if (routeSize >= 2) {
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1800
                )
            )
        }
    }

    return progress.value
}